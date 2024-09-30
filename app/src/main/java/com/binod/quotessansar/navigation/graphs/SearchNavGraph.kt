package com.binod.quotessansar.navigation.graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.binod.quotessansar.data.remote.models.CategoryDataItem
import com.binod.quotessansar.navigation.Graph
import com.binod.quotessansar.navigation.SearchRouteScreen
import com.binod.quotessansar.ui.screens.search.SearchedCategoryScreen
import com.binod.quotessansar.ui.screens.search.SearchedViewModel
import com.google.gson.Gson

fun NavGraphBuilder.searchNavGraph(rootNavController: NavHostController) {
    navigation(
        route = Graph.SEARCH,
        startDestination = SearchRouteScreen.SearchDetail.route
    )
    {
        composable(
            route = "searched_category_screen/{selectedItemJson}",
            arguments = listOf(navArgument("selectedItemJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val jsonString = backStackEntry.arguments?.getString("selectedItemJson")
            val gson = Gson()
            val categoryItem = gson.fromJson(jsonString, CategoryDataItem::class.java)
            SearchedCategoryScreen(categoryItem, navController = rootNavController)
        }


    }
}