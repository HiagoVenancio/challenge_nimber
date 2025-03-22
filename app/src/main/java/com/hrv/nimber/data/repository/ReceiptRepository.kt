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

    suspend fun addReceipt(date: String, amount: Float, photoPath: String) {
        val receipt = ReceiptEntity(
            date = date,
            amount = amount,
            photoPath = photoPath
        )
        receiptDao.insertReceipt(receipt)
    }

    suspend fun deleteReceipt(receipt: ReceiptEntity) {
        receiptDao.deleteReceipt(receipt)
    }
}
