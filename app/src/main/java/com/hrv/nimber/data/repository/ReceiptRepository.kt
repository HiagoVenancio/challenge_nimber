package com.hrv.nimber.data.repository

import com.hrv.nimber.data.local.ReceiptDao
import com.hrv.nimber.data.local.ReceiptEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReceiptRepository @Inject constructor(
    private val receiptDao: ReceiptDao
) {

    fun getReceipts(): Flow<List<ReceiptEntity>> = receiptDao.getAllReceipts()

    suspend fun addReceipt(date: String, amount: Float, photoPaths: List<String>) {
        val receipt = ReceiptEntity(
            date = date,
            amount = amount,
            photoPaths = photoPaths
        )
        receiptDao.insertReceipt(receipt)
    }

    suspend fun deleteReceipt(receipt: ReceiptEntity) {
        receiptDao.deleteReceipt(receipt)
    }
}
