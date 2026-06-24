package com.example.firstproject.di

import org.koin.core.module.Module
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration



/**
 * All non-platform-specific modules. `platformDatabaseModule()` (Android/iOS
 * actual) is appended by each platform's entry point — see
 * androidMain/.../NewsApplication.kt and iosMain/.../MainViewController.kt.
 */
val commonModules: List<Module> = listOf(
    networkModule,
    databaseModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)

/**
 * Shared Koin bootstrap so both platforms initialize the exact same module
 * set plus their own platform module, instead of duplicating the list.
 */
fun initKoin(platformModule: Module, appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(commonModules + platformModule)
    }
}