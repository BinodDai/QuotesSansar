package com.binod.quotessansar.navigation.graphs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.binod.quotessansar.data.local.ThemePreferences
import com.binod.quotessansar.navigation.Graph
import com.binod.quotessansar.ui.screens.main.MainScreen

@Composable
fun RootNavigationGraph() {
    val rootNavController = rememberNavController()
    val context = LocalContext.current
    val themeFlow = ThemePreferences.getThemePreference(context).collectAsState(initial = false)
    val isDarkTheme by themeFlow

    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    ){
        NavHost(
            navController = rootNavController,
            route = Graph.ROOT,
            startDestination = Graph.MAIN
        ) {
            composable(route = Graph.MAIN)
            {
                MainScreen(rootNavController = rootNavController)
            }
            searchNavGraph(rootNavController)
            splashNavGraph(rootNavController)
        }
    }

}