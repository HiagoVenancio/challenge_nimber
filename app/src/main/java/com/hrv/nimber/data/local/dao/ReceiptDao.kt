package com.hrv.nimber.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hrv.nimber.data.local.entities.ReceiptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertReceipt(receipt: ReceiptEntity)

    @Query("SELECT * FROM receipts ORDER BY id DESC")
    fun getAllReceipts(): Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM receipts WHERE id=:itemId")
    fun getReceiptById(itemId: Int): Flow<ReceiptEntity?>

    @Query("DELETE FROM receipts WHERE id=:itemId")
    suspend fun deleteReceiptById(itemId: Int)

}