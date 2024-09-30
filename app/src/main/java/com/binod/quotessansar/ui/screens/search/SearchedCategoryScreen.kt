package com.binod.quotessansar.ui.screens.search

import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.binod.quotessansar.data.local.ThemePreferences
import com.binod.quotessansar.data.remote.models.CategoryDataItem
import com.binod.quotessansar.data.remote.models.QuotesItem
import com.binod.quotessansar.ui.screens.main.HomeViewModel
import com.binod.quotessansar.ui.screens.search.components.SearchedQuotesFullCardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchedCategoryScreen(
    item: CategoryDataItem,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    )
    {

        Scaffold(
            topBar = {
                Surface(
                    shadowElevation = 4.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    TopAppBar(
                        title = {

                            Text(
                                text = item.category.toString(),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                    )
                }
            }) { innerPadding ->
            if (item.quotes.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(text = "No Quotes Found", fontSize = 18.sp)
                }
            } else {
                QuotesListScreen(quotes = item.quotes, modifier = Modifier.padding(innerPadding))
            }
        }

    }

}


@Composable
fun QuotesListScreen(quotes: List<QuotesItem?>, modifier: Modifier = Modifier) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val clipboardManager =
        remember { context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyListState
    ) {
        items(quotes.size) { index ->
            quotes[index]?.let { item ->
                SearchedQuotesFullCardItem(
                    quotesDataItem = item,
                    onBookmarkClick = {

                    },
                    onShareClick = {
                        viewModel.shareQuote(context = context, quote = item.quote.toString())
                    },
                    onCopyClick = {
                        viewModel.copyQuote(item.quote.toString(), clipboardManager)
                    },
                    onImageDownloadClick = {

                    },
                    onShuffleClick = {

                    },
                )
            }
        }
    }

}