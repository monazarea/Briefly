package com.example.firstproject.presentation.news

import org.koin.compose.viewmodel.koinViewModel
import com.example.firstproject.domain.NewsArticle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/**
 * News list screen. Renders directly off `NewsState` (Loading / Success /
 * Error / Empty) and forwards effects (Snackbar) from `NewsViewModel`
 * via a LaunchedEffect collector — the standard MVI -> Compose wiring.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel = koinViewModel(),
    onNavigateToFavorites: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is NewsEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News") },
                actions = {
                    androidx.compose.material3.IconButton(onClick = onNavigateToFavorites) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Filled.Favorite,
                            contentDescription = "View favorites"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val currentState = state) {
                is NewsState.Loading -> LoadingContent()
                is NewsState.Error -> ErrorContent(currentState.message)
                is NewsState.Empty -> EmptyContent()
                is NewsState.Success -> NewsList(
                    articles = currentState.articles,
                    onFavoriteClick = { article ->
                        viewModel.onIntent(NewsIntent.ToggleFavorite(article))
                    }
                )
            }
        }
    }
}

    @Composable
    private fun NewsList(
        articles: List<NewsArticle>,
        onFavoriteClick: (NewsArticle) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(articles, key = { it.id }) { article ->
                NewsItem(article = article, onFavoriteClick = onFavoriteClick)
            }
        }
    }

    @Composable
    private fun LoadingContent() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    @Composable
    private fun ErrorContent(message: String) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Couldn't load news: $message")
        }
    }

    @Composable
    private fun EmptyContent() {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No articles found")
        }
    }