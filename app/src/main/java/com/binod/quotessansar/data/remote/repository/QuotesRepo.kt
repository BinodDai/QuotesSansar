package com.binod.quotessansar.data.remote.repository

import com.binod.quotessansar.data.remote.api.ApiService
import com.binod.quotessansar.data.remote.models.CategoryResponse
import com.binod.quotessansar.data.remote.models.QuotesResponse
import com.binod.quotessansar.utils.Result
import java.io.IOException
import javax.inject.Inject

class QuotesRepo @Inject constructor(private val apiService: ApiService) {
    suspend fun getQuotes(): Result<QuotesResponse> {
        return try {
            val response = apiService.getQuotes()
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(
                "Something went wrong on our end. We’re working to fix it! Please try again in a moment.",
                null
            )
        } catch (e: Exception) {
            Result.Error("Hmm, something unexpected happened. Let’s give it another try.", null)
        }
    }

    suspend fun getCategory(): Result<CategoryResponse> {
        return try {
            val response = apiService.getCategory()
            Result.Success(response)
        } catch (e: IOException) {
            Result.Error(
                "Something went wrong on our end. We’re working to fix it! Please try again in a moment.",
                null
            )
        } catch (e: Exception) {
            Result.Error("Hmm, something unexpected happened. Let’s give it another try.", null)
        }
    }

}