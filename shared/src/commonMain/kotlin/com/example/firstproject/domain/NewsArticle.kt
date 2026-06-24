package com.example.firstproject.domain

/**
 * Pure domain model for a news article.
 * Contains no annotations from Room or Kotlinx Serialization —
 * those concerns live in the data layer's DTOs/Entities and are
 * converted to/from this model via mappers.
 */
data class NewsArticle(
    val id: String,            // Stable derived id (url is used as the natural key from NewsAPI)
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val sourceName: String,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String,   // ISO-8601 string, formatted for display in the UI layer
    val isFavorite: Boolean = false
)