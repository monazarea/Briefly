package com.example.firstproject.presentation.favorites


sealed interface FavoritesIntent {
    data object LoadFavorites : FavoritesIntent
    data class RemoveFavorite(val articleId: String) : FavoritesIntent
}