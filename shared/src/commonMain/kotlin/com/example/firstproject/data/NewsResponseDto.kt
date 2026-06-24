package com.example.firstproject.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * Top-level response from GET https://newsapi.org/v2/everything
 *
 * Example payload:
 * {
 *   "status": "ok",
 *   "totalResults": 9421,
 *   "articles": [ { ... }, ... ]
 * }
 */
@Serializable
data class NewsResponseDto(
    val status: String,
    val totalResults: Int = 0,
    val articles: List<ArticleDto> = emptyList(),
    // Present only when status == "error"
    val code: String? = null,
    val message: String? = null
)

/**
 * Single article entry from the "articles" array.
 *
 * Example:
 * {
 *   "source": { "id": "the-verge", "name": "The Verge" },
 *   "author": "Jane Doe",
 *   "title": "Bitcoin surges past $70,000",
 *   "description": "...",
 *   "url": "https://...",
 *   "urlToImage": "https://...",
 *   "publishedAt": "2026-06-20T10:15:00Z",
 *   "content": "..."
 * }
 */
@Serializable
data class ArticleDto(
    val source: SourceDto,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    val content: String? = null
)

@Serializable
data class SourceDto(
    val id: String? = null,
    val name: String = "Unknown"
)