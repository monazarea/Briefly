package com.example.firstproject.presentation.news

import com.example.firstproject.domain.NewsArticle


/**
 * Exhaustive, mutually-exclusive UI states for the News screen.
 * Compose renders directly off this sealed interface with a `when`,
 * which guarantees every state is handled (loading/error/empty/success).
 */
sealed interface NewsState {
    data object Loading : NewsState
    data class Success(val articles: List<NewsArticle>) : NewsState
    data class Error(val message: String) : NewsState
    data object Empty : NewsState
}