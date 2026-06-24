package com.example.firstproject


import androidx.compose.ui.window.ComposeUIViewController
import com.example.firstproject.di.initKoin
import com.example.firstproject.di.platformDatabaseModule
import platform.UIKit.UIViewController

private var koinStarted = false

fun MainViewController(): UIViewController {
    if (!koinStarted) {
        initKoin(platformModule = platformDatabaseModule())
        koinStarted = true
    }
    return ComposeUIViewController { App() }
}