package com.binod.quotessansar.ui.screens.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binod.quotessansar.base.BaseViewModel
import com.binod.quotessansar.data.remote.models.CategoryDataItem
import com.binod.quotessansar.data.remote.models.QuotesDataItem
import com.binod.quotessansar.data.remote.repository.QuotesRepo
import com.binod.quotessansar.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val quotesRepo: QuotesRepo) : BaseViewModel() {
    private val _quotes = mutableStateOf<Result<List<QuotesDataItem?>>>(Result.Loading())
    val quotes: State<Result<List<QuotesDataItem?>>> = _quotes

    private val _categoryList = mutableStateOf<Result<List<CategoryDataItem?>>>(Result.Loading())
    val categoryList: State<Result<List<CategoryDataItem?>>> = _categoryList
    private var allCategories: List<CategoryDataItem?> = emptyList()

    private val _filteredCategoryList = mutableStateOf<List<CategoryDataItem?>>(emptyList())
    val filteredCategoryList = _filteredCategoryList



    private var isDataFetched = false

    fun fetchQuotes() {
        viewModelScope.launch {
            if (isDataFetched) return@launch

            when (val result = quotesRepo.getQuotes()) {
                is Result.Success -> {
                    _quotes.value = Result.Success(result.data?.data ?: emptyList())
                    isDataFetched = true
                }

                is Result.Error -> {
                    _quotes.value = Result.Error(result.message ?: "An error occurred", null)
                }

                is Result.Loading -> {

                }
            }
        }
    }

    fun fetchCategory() {
        viewModelScope.launch {
            if (isDataFetched) return@launch

            when (val result = quotesRepo.getCategory()) {
                is Result.Success -> {
                    allCategories = result.data?.data ?: emptyList()
                    _categoryList.value = Result.Success(allCategories)
                    _filteredCategoryList.value = allCategories
                    isDataFetched = true
                }

                is Result.Error -> {
                    _categoryList.value = Result.Error(result.message ?: "An error occurred", null)
                }

                is Result.Loading -> {

                }
            }
        }
    }

    fun resetFilteredCategories() {
        _filteredCategoryList.value = allCategories
    }


    fun onSearchQueryChanged(query: String) {
        val searchQuery = query.trim().lowercase()
        _filteredCategoryList.value = if (searchQuery.isEmpty()) {
            allCategories
        } else {
            allCategories.filter {
                it?.category?.lowercase()?.contains(searchQuery) ?: false
            }
        }
    }

}