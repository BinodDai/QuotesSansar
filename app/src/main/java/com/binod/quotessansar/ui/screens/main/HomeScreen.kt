package com.binod.quotessansar.ui.screens.main

import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.binod.quotessansar.data.local.quotes.entity.Quote
import com.binod.quotessansar.data.remote.models.QuotesDataItem
import com.binod.quotessansar.ui.components.QuotesFullCardItem
import com.binod.quotessansar.utils.Result
import com.plcoding.shimmereffectcompose.ShimmerEffect

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val quotes by viewModel.quotes

    LaunchedEffect(Unit) {
        viewModel.fetchQuotes()
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(),
        color = MaterialTheme.colorScheme.surface
    )
    {
        when (val result = quotes) {
            is Result.Loading -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(10) {
                        Spacer(modifier = Modifier.height(16.dp))
                        ShimmerEffect()
                    }

                }
            }

            is Result.Success -> {
                QuotesListScreen(quotes = result.data.orEmpty())
            }

            is Result.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(text = quotes.message.toString())
                }
            }
        }
    }
}

@Composable
fun QuotesListScreen(quotes: List<QuotesDataItem?>) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    val clipboardManager =
        remember { context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyListState
    ) {
        items(quotes.size) { index ->
            quotes[index]?.let { item ->
                QuotesFullCardItem(
                    quotesDataItem = item,
                    onBookmarkClick = {
                        viewModel.insertQuote(
                            Quote(
                                quoteId = item.id ?: 0,
                                quotes = item.quote.toString(),
                                author = item.author.toString()
                            )
                        )
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