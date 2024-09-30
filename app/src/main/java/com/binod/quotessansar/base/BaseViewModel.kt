package com.binod.quotessansar.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _copiedQuote = mutableStateOf<String?>(null)
    val copiedQuote: State<String?> = _copiedQuote

    private val _shareQuote = mutableStateOf<String?>(null)
    val shareQuote: State<String?> = _shareQuote

    fun copyQuote(quote: String, clipboardManager: ClipboardManager) {
        viewModelScope.launch {
            val clip = ClipData.newPlainText("Copied Quote:", quote)
            clipboardManager.setPrimaryClip(clip)
            _copiedQuote.value = quote
        }
    }

    fun shareQuote(context: Context, quote: String) {
        viewModelScope.launch {
            _shareQuote.value = quote
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, quote)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    }
}
