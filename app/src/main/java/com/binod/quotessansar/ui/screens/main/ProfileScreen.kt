package com.binod.quotessansar.ui.screens.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.binod.quotessansar.data.local.quotes.entity.Quote

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {
    val quotes by viewModel.allQuotes.collectAsState(initial = emptyList())

    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
            .fillMaxSize()
            .padding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(quotes) { quote ->
                    QuoteCard(quote = quote)
                }
            }
        }

    }
}


@Composable
fun QuoteCard(quote: Quote) {
    val viewModel: HomeViewModel = hiltViewModel()
    val context = LocalContext.current
    Surface(
        modifier = Modifier.padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            RowItem(quote.quotes, Icons.Outlined.Share) {
                viewModel.shareQuote(context = context, quote.quotes)
            }
            RowItem(quote.author, Icons.Outlined.Delete) {
                viewModel.deleteQuoteById(quote.id)
            }

        }
    }
}

@Composable
fun RowItem(text: String, icon: ImageVector, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface
        )

        Icon(
            imageVector = icon,
            contentDescription = "Icon",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .size(24.dp)
                .clickable(enabled = true, onClick = { onItemClick() })
        )
    }
}