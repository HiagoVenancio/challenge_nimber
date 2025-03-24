package com.hrv.nimber.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hrv.nimber.data.local.dao.ReceiptDao
import com.hrv.nimber.data.local.entities.ReceiptEntity

@Database(entities = [ReceiptEntity::class], version = 4, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun receiptDao(): ReceiptDao
}
