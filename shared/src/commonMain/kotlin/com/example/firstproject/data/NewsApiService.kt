package com.example.firstproject.data


import com.example.firstproject.BuildKonfig
import com.example.firstproject.data.NewsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter


/**
 * Thin wrapper around Ktor's HttpClient for the NewsAPI "everything" endpoint.
 *
 * The API key is NEVER hardcoded here. It's pulled from BuildKonfig, which is
 * generated at build time from gradle.properties / local.properties (see
 * BuildKonfig setup below). This keeps secrets out of version control while
 * still making them available as a typed constant at compile time.
 */
class NewsApiService(
    private val httpClient: HttpClient
) {
    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/everything"
    }

    /**
     * Calls GET https://newsapi.org/v2/everything?q={query}&apiKey={BuildKonfig.NEWS_API_KEY}
     */
    suspend fun getEverything(query: String): NewsResponseDto {
        return httpClient.get(BASE_URL) {
            parameter("q", query)
            parameter("sortBy", "publishedAt")
            parameter("language", "en")
            parameter("apiKey", BuildKonfig.NEWS_API_KEY)
        }.body()
    }
}