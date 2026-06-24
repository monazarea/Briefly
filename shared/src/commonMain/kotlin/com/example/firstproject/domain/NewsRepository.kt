package com.example.firstproject.domain

import kotlinx.coroutines.flow.Flow

/**
 * Contract owned by the domain layer. The data layer provides the
 * implementation (NewsRepositoryImpl), keeping domain free of any
 * Ktor/Room dependency — this is the core of the Dependency Inversion
 * Principle in Clean Architecture.
 */
interface NewsRepository {

    /**
     * Fetches articles from the NewsAPI "everything" endpoint for the given query.
     * Throws on network/parsing failure — the use case / ViewModel decides how to
     * translate that into UI state.
     */
    suspend fun getNews(query: String): List<NewsArticle>

    /**
     * Observes the favorites table in Room. Emits a new list any time the
     * underlying data changes (insert/delete), so the UI stays reactive.
     */
    fun observeFavorites(): Flow<List<NewsArticle>>

    /**
     * Adds an article to favorites. Implementation is responsible for
     * preventing duplicate entries (e.g. via Room's OnConflictStrategy.IGNORE
     * or a pre-check using the unique url).
     */
    suspend fun addFavorite(article: NewsArticle)

    /**
     * Removes an article from favorites by its unique url/id.
     */
    suspend fun removeFavorite(articleId: String)

    /**
     * Checks whether a given article is already saved as a favorite.
     * Used by the News screen to render the correct favorite icon state.
     */
    suspend fun isFavorite(articleId: String): Boolean
}