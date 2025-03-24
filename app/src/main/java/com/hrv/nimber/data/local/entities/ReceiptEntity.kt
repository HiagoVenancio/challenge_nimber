package com.hrv.nimber.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ReceiptEntity.Companion.TABLE_NAME)
data class ReceiptEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val amount: Float,
    val photoPaths: List<String>
){
    companion object{
        const val TABLE_NAME = "receipts"
    }
}