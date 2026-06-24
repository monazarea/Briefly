package com.example.firstproject.presentation.news

import com.example.firstproject.domain.NewsArticle


/**
 * All user-triggered or lifecycle-triggered actions on the News screen.
 */
sealed interface NewsIntent {
    data object LoadNews : NewsIntent
    data class ToggleFavorite(val article: NewsArticle) : NewsIntent
}