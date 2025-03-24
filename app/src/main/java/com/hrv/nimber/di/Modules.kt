package com.hrv.nimber.di

import android.content.Context
import androidx.room.Room
import com.hrv.nimber.data.local.AppDatabase
import com.hrv.nimber.data.local.dao.ReceiptDao
import com.hrv.nimber.data.repository.IReceiptRepository
import com.hrv.nimber.data.repository.ReceiptRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

}*/

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideReceiptDao(database: AppDatabase): ReceiptDao = database.receiptDao()

    @Provides
    @Singleton
    fun provideReceiptRepository(receiptDao: ReceiptDao): IReceiptRepository {
        return ReceiptRepository(receiptDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "receipts_db"
        )
            .fallbackToDestructiveMigration()
            .build()


}
