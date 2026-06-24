package com.example.firstproject.di

import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel
import com.example.firstproject.presentation.favorites.FavoritesViewModel
import com.example.firstproject.presentation.news.NewsViewModel




/**
 * Uses Koin's Compose Multiplatform `viewModel { }` DSL so instances are
 * scoped correctly to the navigation back stack entry and survive
 * recomposition, matching `koinViewModel()` calls in NewsScreen/FavoritesScreen.
 */
val viewModelModule = module {
    viewModel {
        NewsViewModel(
            getNewsUseCase = get(),
            addFavoriteUseCase = get(),
            removeFavoriteUseCase = get()
        )
    }
    viewModel {
        FavoritesViewModel(
            getFavoritesUseCase = get(),
            removeFavoriteUseCase = get()
        )
    }
}