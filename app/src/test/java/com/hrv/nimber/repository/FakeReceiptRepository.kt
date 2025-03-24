package com.hrv.nimber.repository

import com.hrv.nimber.data.local.entities.ReceiptEntity
import com.hrv.nimber.data.repository.IReceiptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeReceiptRepository : IReceiptRepository {
    private val receiptsFlow = MutableStateFlow<List<ReceiptEntity>>(emptyList())

    override fun getReceipts(): Flow<List<ReceiptEntity>> = receiptsFlow

    override suspend fun addReceipt(date: String, amount: Float, photoPath: List<String>) {
        val newId = receiptsFlow.value.size + 1
        val newReceipt = ReceiptEntity(
            id = newId,
            date = date,
            amount = amount,
            photoPaths = photoPath
        )
        receiptsFlow.value = receiptsFlow.value + newReceipt
    }

    override suspend fun deleteReceipt(receiptId: Int) {
        val receipt = receiptsFlow.value.first { it.id == receiptId }
        receiptsFlow.value -= receipt
    }

    override fun getReceiptById(itemId: Int): Flow<ReceiptEntity?> {
        return MutableStateFlow(receiptsFlow.value.find { it.id == itemId })
    }
}