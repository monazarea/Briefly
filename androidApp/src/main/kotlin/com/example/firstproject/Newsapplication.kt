package com.example.firstproject


import android.app.Application
import com.example.firstproject.di.initKoin
import com.example.firstproject.di.platformDatabaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(platformModule = platformDatabaseModule()) {
            androidLogger()
            androidContext(this@NewsApplication)
        }
    }
}