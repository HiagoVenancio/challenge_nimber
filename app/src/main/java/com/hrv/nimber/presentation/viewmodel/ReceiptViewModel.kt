package com.hrv.nimber.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrv.nimber.data.local.ReceiptEntity
import com.hrv.nimber.data.mapper.toUiModel
import com.hrv.nimber.data.repository.ReceiptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val repository: ReceiptRepository
) : ViewModel() {

    val receipts: StateFlow<List<ReceiptsUiModel>> =
        repository.getReceipts()
            .map { entityList ->
                entityList.map {
                    it.toUiModel()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )

    fun createImageFileUri(context: Context): Uri? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)
    }

    fun addReceipt(date: String, amount: Float, photoPath: String) {
        viewModelScope.launch {
            repository.addReceipt(date, amount, photoPath)
        }
    }

    fun deleteReceipt(receipt: ReceiptEntity) {
        viewModelScope.launch {
            repository.deleteReceipt(receipt)
        }
    }
}

data class ReceiptsUiModel(
    val id: Int = 0,
    val date: String = "",
    val amount: Float = 0.0f,
    val photoPath: String = ""
)
