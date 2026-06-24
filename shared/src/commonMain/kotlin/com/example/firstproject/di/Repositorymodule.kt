package com.example.firstproject.di

import com.example.firstproject.data.NewsRepositoryImpl
import com.example.firstproject.domain.NewsRepository

import org.koin.dsl.module

val repositoryModule = module {
    single<NewsRepository> {
        NewsRepositoryImpl(
            apiService = get(),
            favoriteDao = get()
        )
    }
}