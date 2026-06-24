package com.example.firstproject.presentation.news


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstproject.domain.NewsArticle
import com.example.firstproject.domain.AddFavoriteUseCase
import com.example.firstproject.domain.GetNewsUseCase
import com.example.firstproject.domain.RemoveFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * MVI ViewModel for the News screen.
 *
 * - `state`   : StateFlow<NewsState> — the single source of truth the UI renders.
 * - `effect`  : Flow<NewsEffect>     — one-shot events (Snackbars) consumed once.
 * - `onIntent`: the single entry point for all user actions, per MVI convention.
 */
class NewsViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<NewsState>(NewsState.Loading)
    val state: StateFlow<NewsState> = _state.asStateFlow()

    private val _effect = Channel<NewsEffect>(Channel.BUFFERED)
    val effect: Flow<NewsEffect> = _effect.receiveAsFlow()

    init {
        onIntent(NewsIntent.LoadNews)
    }

    fun onIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.LoadNews -> loadNews()
            is NewsIntent.ToggleFavorite -> toggleFavorite(intent.article)
        }
    }

    private fun loadNews() {
        viewModelScope.launch {
            _state.value = NewsState.Loading
            try {
                val articles = getNewsUseCase(query = "bitcoin")
                _state.value = if (articles.isEmpty()) {
                    NewsState.Empty
                } else {
                    NewsState.Success(articles)
                }
            } catch (e: Exception) {
                _state.value = NewsState.Error(e.message ?: "Something went wrong while loading news")
            }
        }
    }

    private fun toggleFavorite(article: NewsArticle) {
        viewModelScope.launch {
            val result = if (article.isFavorite) {
                removeFavoriteUseCase(article.id).map { "Removed from favorites" }
            } else {
                addFavoriteUseCase(article).map { "Added to favorites" }
            }

            result.onSuccess { message ->
                _effect.send(NewsEffect.ShowSnackbar(message))
                updateArticleFavoriteFlag(article.id, isFavorite = !article.isFavorite)
            }.onFailure { error ->
                _effect.send(
                    NewsEffect.ShowSnackbar(error.message ?: "Could not update favorites")
                )
            }
        }
    }

    /**
     * Optimistically flips the favorite flag for one article in the current
     * Success state, so the icon updates immediately without a full reload.
     */
    private fun updateArticleFavoriteFlag(articleId: String, isFavorite: Boolean) {
        val currentState = _state.value
        if (currentState is NewsState.Success) {
            val updated = currentState.articles.map {
                if (it.id == articleId) it.copy(isFavorite = isFavorite) else it
            }
            _state.value = NewsState.Success(updated)
        }
    }
}