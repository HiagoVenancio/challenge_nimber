package com.hrv.nimber.data.repository

import com.hrv.nimber.data.local.dao.ReceiptDao
import com.hrv.nimber.data.local.entities.ReceiptEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface IReceiptRepository{
    fun getReceipts(): Flow<List<ReceiptEntity>>
    suspend fun addReceipt(date: String, amount: Float, photoPaths: List<String>)
    fun getReceiptById(itemId: Int): Flow<ReceiptEntity?>
    suspend fun deleteReceipt(itemId: Int)
}

@Singleton
class ReceiptRepository @Inject constructor(
    private val receiptDao: ReceiptDao
) : IReceiptRepository {

    override fun getReceipts(): Flow<List<ReceiptEntity>> = receiptDao.getAllReceipts()

    override suspend fun addReceipt(date: String, amount: Float, photoPaths: List<String>) {
        val receipt = ReceiptEntity(
            date = date,
            amount = amount,
            photoPaths = photoPaths
        )
        receiptDao.insertReceipt(receipt)
    }

    override fun getReceiptById(itemId: Int): Flow<ReceiptEntity?> {
        return receiptDao.getReceiptById(itemId)
    }

    override suspend fun deleteReceipt(itemId: Int) {
        receiptDao.deleteReceiptById(itemId)
    }
}
