package com.example.firstproject.di



import com.example.firstproject.domain.AddFavoriteUseCase
import com.example.firstproject.domain.GetFavoritesUseCase
import com.example.firstproject.domain.GetNewsUseCase
import com.example.firstproject.domain.RemoveFavoriteUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetNewsUseCase(repository = get()) }
    factory { GetFavoritesUseCase(repository = get()) }
    factory { AddFavoriteUseCase(repository = get()) }
    factory { RemoveFavoriteUseCase(repository = get()) }
}