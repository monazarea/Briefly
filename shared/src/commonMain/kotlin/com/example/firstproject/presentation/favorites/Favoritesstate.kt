package com.example.firstproject.presentation.favorites

import com.example.firstproject.domain.NewsArticle


sealed interface FavoritesState {
    data object Loading : FavoritesState
    data class Success(val articles: List<NewsArticle>) : FavoritesState
    data object Empty : FavoritesState
}