package com.binod.quotessansar.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.binod.quotessansar.R
import com.binod.quotessansar.data.local.ThemePreferences
import com.binod.quotessansar.navigation.BottomBarScreen
import com.binod.quotessansar.navigation.graphs.MainNavGraph
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    homeNavController: NavHostController = rememberNavController()

) {
    val context = LocalContext.current
    val themeFlow = ThemePreferences.getThemePreference(context).collectAsState(initial = false)
    val isDarkTheme by themeFlow

    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scope = rememberCoroutineScope()
    val onThemeToggle = {
        scope.launch {
            ThemePreferences.saveThemePreference(context, !isDarkTheme)
        }
    }

    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
    ) {
        Scaffold(
            bottomBar = { BottomBar(navController = homeNavController) },
            topBar = {
                TopAppBar(
                    currentRoute = currentRoute,
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = onThemeToggle
                )
            }
        ) { innerPadding ->
            MainNavGraph(
                rootNavHostController = rootNavController,
                homeNavController = homeNavController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Search,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(
            tonalElevation = 8.dp,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    currentRoute: String?,
    isDarkTheme: Boolean,
    onThemeToggle: () -> Job
) {
    Surface(
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        if (currentRoute != BottomBarScreen.Search.route) {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.quotes_sansar),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    Image(
                        painter = if (isDarkTheme) painterResource(id = R.drawable.ic_sun) else painterResource(
                            id = R.drawable.ic_moon
                        ),
                        contentDescription = if (isDarkTheme) stringResource(id = R.string.light_mode) else stringResource(
                            id = R.string.dark_mode
                        ),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .size(30.dp)
                            .clickable { onThemeToggle() }
                    )
                },

                )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    NavigationBarItem(
        selected = isSelected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = {
            Icon(
                imageVector = if (isSelected) screen.selectedIcon else screen.unSelectedIcon,
                contentDescription = screen.title
            )
        },
        label = {
            Text(text = screen.title)
        }
    )
}


