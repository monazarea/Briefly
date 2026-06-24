package com.example.firstproject.presentation.favorites


sealed interface FavoritesEffect {
    data class ShowSnackbar(val message: String) : FavoritesEffect
}