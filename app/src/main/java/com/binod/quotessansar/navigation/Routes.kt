package com.binod.quotessansar.navigation

object Graph {
    const val ROOT = "root_graph"
    const val MAIN = "main_graph"
    const val SEARCH = "search_graph"
    const val SPLASH = "splash_graph"
}

sealed class MainRouteScreen(val route: String) {
    data object Home : MainRouteScreen("home")
    data object Search : MainRouteScreen("search")
    data object Profile : MainRouteScreen("profile")

}

sealed class SearchRouteScreen(val route: String) {
    data object SearchDetail : SearchRouteScreen("searchDetail")
}

sealed class SplashRouteScreen(val route: String) {
    data object Splash : SplashRouteScreen("splash")
}