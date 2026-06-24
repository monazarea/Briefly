package com.example.firstproject.di

import com.example.firstproject.data.NewsDatabase
import org.koin.dsl.module


/**
 * Common database module. The actual NewsDatabase instance is supplied by
 * `platformDatabaseModule()`, an expect/actual function implemented per
 * platform (Android needs a Context to resolve a file path; iOS resolves
 * its own Documents directory). This module then exposes the DAO from
 * whichever NewsDatabase instance was provided.
 */
val databaseModule = module {
    single { get<NewsDatabase>().favoriteDao() }
}

/**
 * Implemented in androidMain/iosMain. Must be included in the Koin
 * `startKoin { modules(...) }` call ALONGSIDE [databaseModule] — see
 * AppModule.kt and the platform-specific Application/iOS entry points.
 */
expect fun platformDatabaseModule(): org.koin.core.module.Module