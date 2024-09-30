package com.binod.quotessansar.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.binod.quotessansar.data.local.QuotesDatabase
import com.binod.quotessansar.data.local.quotes.dao.QuoteDao
import com.binod.quotessansar.data.local.quotes.repo.QuoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): QuotesDatabase {
        return Room.databaseBuilder(
            context,
            QuotesDatabase::class.java,
            "quotes_db"
        ).build()
    }

    @Provides
    fun provideQuoteDao(database: QuotesDatabase) = database.quoteDao()

    @Provides
    fun provideQuoteRepository(dao: QuoteDao): QuoteRepository {
        return QuoteRepository(dao)
    }

}