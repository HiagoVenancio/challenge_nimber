package com.hrv.nimber.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hrv.nimber.data.local.ReceiptEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ReceiptEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val amount: Float,
    val photoPath: String
){
    companion object{
        const val TABLE_NAME = "receipts"
    }
}
