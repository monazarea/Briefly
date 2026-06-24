package com.example.firstproject.data


import com.example.firstproject.domain.NewsArticle


/**
 * DTO -> Domain
 * The article's `url` is used as the stable `id` since NewsAPI doesn't
 * provide its own numeric identifier and url is unique per article.
 */
fun ArticleDto.toDomain(): NewsArticle = NewsArticle(
    id = url,
    title = title,
    description = description,
    content = content,
    author = author,
    sourceName = source.name,
    url = url,
    imageUrl = urlToImage,
    publishedAt = publishedAt,
    isFavorite = false // resolved separately against the favorites table
)

/**
 * Entity -> Domain
 * Anything loaded from the favorites table is, by definition, a favorite.
 */
fun FavoriteArticleEntity.toDomain(): NewsArticle = NewsArticle(
    id = id,
    title = title,
    description = description,
    content = content,
    author = author,
    sourceName = sourceName,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    isFavorite = true
)

/**
 * Domain -> Entity
 * Used when persisting an article the user tapped "favorite" on.
 */
fun NewsArticle.toEntity(): FavoriteArticleEntity = FavoriteArticleEntity(
    id = id,
    title = title,
    description = description,
    content = content,
    author = author,
    sourceName = sourceName,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt
)