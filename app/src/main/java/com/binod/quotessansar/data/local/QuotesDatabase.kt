package com.binod.quotessansar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binod.quotessansar.data.local.quotes.dao.QuoteDao
import com.binod.quotessansar.data.local.quotes.entity.Quote

@Database(entities = [Quote::class], version = 1, exportSchema = false)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao
}

