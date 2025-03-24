package com.hrv.nimber.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrv.nimber.data.mapper.toUiModel
import com.hrv.nimber.data.repository.ReceiptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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


    private val _detailReceipt = MutableStateFlow<ReceiptsUiModel?>(null)
    val detailReceipt: StateFlow<ReceiptsUiModel?> = _detailReceipt

    fun addReceipt(date: String, amount: Float, photoPath: List<String>) {
        viewModelScope.launch {
            repository.addReceipt(date, amount, photoPath)
        }
    }

    fun deleteReceipt(receipt: Int) {
        viewModelScope.launch {
            repository.deleteReceipt(receipt)
        }
    }

    fun getReceiptById(itemId: Int) {
        viewModelScope.launch {
            repository.getReceiptById(itemId)
                .collect {
                    _detailReceipt.value = it?.toUiModel()
                }
        }
    }
}

data class ReceiptsUiModel(
    val id: Int = 0,
    val date: String = "",
    val amount: Float = 0.0f,
    val photoPath: List<String> = emptyList()
)
