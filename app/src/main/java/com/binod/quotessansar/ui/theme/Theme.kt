package com.binod.quotessansar.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Light Theme Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFFFFFF),    // Pure white background
    onPrimary = Color(0xFF202124),  // Google dark gray text on white
    surface = Color(0xFFF1F3F4),    // Light gray surface for cards, etc.
    onSurface = Color(0xFF202124),  // Dark gray text/icons on surface
    secondary = Color(0xFF34A853),  // Google Play green for accents and buttons
    onSecondary = Color(0xFFFFFFFF),// White text/icons on secondary (green)
    error = Color(0xFFEA4335),      // Google red for errors
    onError = Color(0xFFFFFFFF)
)

// Dark Theme Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF121212),    // Dark gray/black background
    onPrimary = Color(0xFFE8EAED),  // Light gray text on dark background
    surface = Color(0xFF1F1F1F),    // Dark gray surface for cards, etc.
    onSurface = Color(0xFFE8EAED),  // Light gray text/icons on surface
    secondary = Color(0xFF34A853),  // Google Play green for accents and buttons
    onSecondary = Color(0xFF121212),// Dark background for text on secondary (green)
    error = Color(0xFFEA4335),      // Google red for errors
    onError = Color(0xFFFFFFFF)     // White text/icons on error
)


@Composable
fun QuotesSansarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}