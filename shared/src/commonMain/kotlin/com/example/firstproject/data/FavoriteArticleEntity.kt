package com.example.firstproject.data


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity representing a saved favorite article.
 *
 * `id` is the article's url — NewsAPI doesn't provide a numeric id, and the
 * url is guaranteed unique per article, so it doubles as a natural primary
 * key and the mechanism for duplicate prevention (see FavoriteDao.insert).
 */
@Entity(tableName = "favorite_articles")
data class FavoriteArticleEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val sourceName: String,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String
)