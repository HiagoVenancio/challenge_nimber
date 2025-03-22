package com.hrv.nimber.presentation.viewmodel

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
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val repository: ReceiptRepository
) : ViewModel() {

    // Expose receipts as a StateFlow
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

    fun addReceipt(date: String, amount: Double, currency: String, photoPath: String) {
        viewModelScope.launch {
            repository.addReceipt(date, amount, currency, photoPath)
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
    val amount: Double = 0.0,
    val currency: String = "",
    val photoPath: String = ""
)
