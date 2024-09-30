package com.binod.quotessansar.ui.screens.main

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.binod.quotessansar.data.remote.models.CategoryDataItem
import com.binod.quotessansar.ui.components.CategorySearchBar
import com.binod.quotessansar.utils.Result
import com.binod.quotessansar.utils.toCamelCase
import com.google.gson.Gson

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    rootNavController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val categoryList by viewModel.categoryList
    val filteredCategoryList by viewModel.filteredCategoryList
    LaunchedEffect(Unit) {
        viewModel.fetchCategory()
        viewModel.resetFilteredCategories()
    }
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(32.dp))
        CategorySearchBar(modifier = Modifier) { query ->
            viewModel.onSearchQueryChanged(query)
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {

            when (val result = categoryList) {
                is Result.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }

                    }
                }

                is Result.Success -> {
                    CategoryListScreen(category = filteredCategoryList) { item ->
                        val gson = Gson()
                        val selectedItemJson = gson.toJson(item)
                        rootNavController.navigate(
                            "searched_category_screen/${
                                Uri.encode(
                                    selectedItemJson
                                )
                            }"
                        )

                    }
                }

                is Result.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(text = categoryList.message.toString())
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {


            }
        }
    }
}

@Composable
fun CategoryListScreen(category: List<CategoryDataItem?>, onItemClick: (CategoryDataItem) -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(category.size) { index ->
                category[index]?.let { item ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemClick(item)
                            },
                        shape = RoundedCornerShape(8.dp),
                        tonalElevation = 2.dp,
                        shadowElevation = 2.dp,
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(vertical = 12.dp, horizontal = 12.dp)
                        ) {
                            Text(
                                text = item.category?.toCamelCase() ?: "Unknown Category",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 16.sp
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

