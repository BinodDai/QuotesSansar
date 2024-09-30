package com.binod.quotessansar.data.remote.api

import com.binod.quotessansar.data.remote.models.CategoryResponse
import com.binod.quotessansar.data.remote.models.QuotesResponse
import retrofit2.http.GET

interface ApiService {
    @GET("quotes")
    suspend fun getQuotes(): QuotesResponse

    @GET("category")
    suspend fun getCategory(): CategoryResponse
}