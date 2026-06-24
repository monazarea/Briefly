package com.example.firstproject.data



import com.example.firstproject.domain.NewsArticle
import com.example.firstproject.domain.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Single source of truth that reconciles the remote API (Ktor) with local
 * persistence (Room). The domain layer only ever sees NewsArticle — all
 * DTO/Entity details are mapped away here.
 */
class NewsRepositoryImpl(
    private val apiService: NewsApiService,
    private val favoriteDao: FavoriteDao
) : NewsRepository {

    override suspend fun getNews(query: String): List<NewsArticle> {
        val response = apiService.getEverything(query)

        if (response.status != "ok") {
            throw NewsApiException(
                message = response.message ?: "Unknown error from NewsAPI (code=${response.code})"
            )
        }

        return response.articles.map { dto ->
            val article = dto.toDomain()
            // Resolve favorite state per-article so the News screen renders
            // the correct filled/outline icon without a separate round trip.
            article.copy(isFavorite = favoriteDao.exists(article.id))
        }
    }

    override fun observeFavorites(): Flow<List<NewsArticle>> =
        favoriteDao.observeAll().map { entities -> entities.map{ it.toDomain() } }

    override suspend fun addFavorite(article: NewsArticle) {
        favoriteDao.insert(article.toEntity())
    }

    override suspend fun removeFavorite(articleId: String) {
        favoriteDao.deleteById(articleId)
    }

    override suspend fun isFavorite(articleId: String): Boolean =
        favoriteDao.exists(articleId)
}

class NewsApiException(message: String) : Exception(message)