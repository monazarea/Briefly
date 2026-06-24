package com.example.firstproject.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.firstproject.presentation.news.NewsScreen
import com.example.firstproject.presentation.favorites.FavoritesScreen

/**
 * Two-destination graph: News (start) <-> Favorites.
 * Uses androidx.navigation's Compose Multiplatform artifact, shared as-is
 * across Android/iOS via commonMain.
 */
object Routes {
    const val NEWS = "news"
    const val FAVORITES = "favorites"
}

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.NEWS) {
        composable(Routes.NEWS) {
            NewsScreen(
                onNavigateToFavorites = { navController.navigate(Routes.FAVORITES) }
            )
        }
        composable(Routes.FAVORITES) {
            FavoritesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}