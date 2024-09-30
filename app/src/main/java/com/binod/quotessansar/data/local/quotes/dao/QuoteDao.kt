package com.binod.quotessansar.data.local.quotes.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.binod.quotessansar.data.local.quotes.entity.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert
    suspend fun insertQuote(quote: Quote)

    @Query("SELECT * FROM quotes")
    fun getAllQuotes(): Flow<List<Quote>>

    @Query("DELETE FROM quotes WHERE id = :quoteId")
    suspend fun deleteQuoteById(quoteId: Int)
}