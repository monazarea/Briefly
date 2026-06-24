package com.example.firstproject.presentation.news


/**
 * One-shot, non-state events the ViewModel pushes to the UI — things that
 * should happen exactly once and never survive a configuration change
 * (e.g. a Snackbar), unlike State which is re-rendered every time.
 */
sealed interface NewsEffect {
    data class ShowSnackbar(val message: String) : NewsEffect
}