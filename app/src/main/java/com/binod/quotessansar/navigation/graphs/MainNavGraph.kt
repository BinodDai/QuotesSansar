package com.binod.quotessansar.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.binod.quotessansar.navigation.Graph
import com.binod.quotessansar.navigation.MainRouteScreen
import com.binod.quotessansar.ui.screens.main.HomeViewModel
import com.binod.quotessansar.ui.screens.main.HomeScreen
import com.binod.quotessansar.ui.screens.main.ProfileScreen
import com.binod.quotessansar.ui.screens.main.SearchScreen

@Composable
fun MainNavGraph(
    rootNavHostController: NavHostController,
    homeNavController: NavHostController,
    modifier: Modifier
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    NavHost(
        navController = homeNavController,
        route = Graph.MAIN,
        startDestination = MainRouteScreen.Home.route,
    )
    {
        composable(route = MainRouteScreen.Home.route)
        {
            HomeScreen(modifier = modifier)
        }

        composable(route = MainRouteScreen.Search.route)
        {
            SearchScreen(modifier = modifier, rootNavHostController, homeViewModel)
        }

        composable(route = MainRouteScreen.Profile.route)
        {
            ProfileScreen()
        }
    }

}
