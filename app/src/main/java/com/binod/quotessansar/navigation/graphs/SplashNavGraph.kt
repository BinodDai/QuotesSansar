package com.binod.quotessansar.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.binod.quotessansar.navigation.Graph
import com.binod.quotessansar.navigation.SplashRouteScreen

fun NavGraphBuilder.splashNavGraph(rootNavController: NavHostController) {
    navigation(
        route = Graph.SPLASH,
        startDestination = SplashRouteScreen.Splash.route
    )
    {
        composable(route = SplashRouteScreen.Splash.route)
        {

        }

    }
}