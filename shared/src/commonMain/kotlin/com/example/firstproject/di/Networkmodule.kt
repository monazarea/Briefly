package com.example.firstproject.di


import io.ktor.client.HttpClient
import org.koin.dsl.module
import com.example.firstproject.data.NewsApiService
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Networking module: a single shared HttpClient configured with
 * ContentNegotiation (kotlinx.serialization), Logging, and DefaultRequest,
 * plus the NewsApiService that consumes it.
 *
 * The HttpClientEngine itself is supplied by the platform-specific Ktor
 * engine artifact on the classpath (OkHttp on Android, Darwin on iOS) —
 * `HttpClient { ... }` picks it up automatically, so no expect/actual is
 * needed here.
 */
val networkModule = module {

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        coerceInputValues = true
                    }
                )
            }

            install(Logging) {
                level = LogLevel.INFO
            }

            install(DefaultRequest) {
                contentType(ContentType.Application.Json)
                header("Accept", "application/json")
            }
        }
    }

    single { NewsApiService(httpClient = get()) }
}