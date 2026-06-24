package com.example.firstproject.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstproject.domain.GetFavoritesUseCase
import com.example.firstproject.domain.RemoveFavoriteUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


/**
 * MVI ViewModel for the Favorites screen.
 *
 * Unlike News (a one-shot fetch), Favorites is driven by a continuously
 * observed Room Flow — `collectFavorites()` subscribes once in `init` and
 * the state updates automatically whenever the table changes, satisfying
 * "Observe database changes using Flow / Update UI automatically".
 */
class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FavoritesState>(FavoritesState.Loading)
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    private val _effect = Channel<FavoritesEffect>(Channel.BUFFERED)
    val effect: Flow<FavoritesEffect> = _effect.receiveAsFlow()

    init {
        onIntent(FavoritesIntent.LoadFavorites)
    }

    fun onIntent(intent: FavoritesIntent) {
        when (intent) {
            is FavoritesIntent.LoadFavorites -> collectFavorites()
            is FavoritesIntent.RemoveFavorite -> removeFavorite(intent.articleId)
        }
    }

    private fun collectFavorites() {
        viewModelScope.launch {
            getFavoritesUseCase()
                .onEach { articles ->
                    _state.value = if (articles.isEmpty()) {
                        FavoritesState.Empty
                    } else {
                        FavoritesState.Success(articles)
                    }
                }
                .catch { e ->
                    // Favorites has no dedicated Error state per spec; surface
                    // failures via the Snackbar effect instead and fall back to Empty.
                    _effect.send(FavoritesEffect.ShowSnackbar(e.message ?: "Failed to load favorites"))
                    _state.value = FavoritesState.Empty
                }
                .collect()
        }
    }

    private fun removeFavorite(articleId: String) {
        viewModelScope.launch {
            removeFavoriteUseCase(articleId)
                .onSuccess {
                    _effect.send(FavoritesEffect.ShowSnackbar("Removed from favorites"))
                    // No manual state mutation needed — the Room Flow collected
                    // above will automatically emit the updated list.
                }
                .onFailure { e ->
                    _effect.send(FavoritesEffect.ShowSnackbar(e.message ?: "Could not remove favorite"))
                }
        }
    }
}