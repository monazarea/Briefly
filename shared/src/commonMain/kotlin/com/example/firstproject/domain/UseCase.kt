package com.example.firstproject.domain

import com.example.firstproject.domain.NewsArticle
import com.example.firstproject.domain.NewsRepository
import kotlinx.coroutines.flow.Flow
/**
 * Encapsulates "fetch news for a query" as a single, testable action.
 * ViewModels depend on this instead of the repository directly, which
 * keeps presentation decoupled from how data is actually sourced.
 */
class GetNewsUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(query: String = "bitcoin"): List<NewsArticle> {
        require(query.isNotBlank()) { "Query must not be blank" }
        return repository.getNews(query)
    }
}

class GetFavoritesUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<List<NewsArticle>> = repository.observeFavorites()
}

class AddFavoriteUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(article: NewsArticle): Result<Unit> {
        return try {
            val alreadyExists = repository.isFavorite(article.id)
            if (alreadyExists) {
                Result.failure(IllegalStateException("Article is already in favorites"))
            } else {
                repository.addFavorite(article)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class RemoveFavoriteUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(articleId: String): Result<Unit> {
        return try {
            repository.removeFavorite(articleId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}