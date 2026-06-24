package com.example.firstproject.di

import com.example.firstproject.data.NewsDatabase
import com.example.firstproject.data.build
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformDatabaseModule(): Module = module {
    single<NewsDatabase> { NewsDatabase.build() }
}
