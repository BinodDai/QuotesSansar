package com.binod.quotessansar.data.local.quotes.repo

import com.binod.quotessansar.data.local.quotes.dao.QuoteDao
import com.binod.quotessansar.data.local.quotes.entity.Quote
import dagger.Provides
import kotlinx.coroutines.flow.Flow

class QuoteRepository(private val quoteDao: QuoteDao) {

    suspend fun insertQuote(quote: Quote) {
        quoteDao.insertQuote(quote)
    }

    fun getAllQuotes(): Flow<List<Quote>> {
        return quoteDao.getAllQuotes()
    }

    suspend fun deleteQuoteById(quoteId: Int) {
        quoteDao.deleteQuoteById(quoteId)
    }
}
