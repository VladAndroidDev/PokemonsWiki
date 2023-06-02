package com.nevi.pokemonswiki.compose.bottom_bar.berries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nevi.pokemonswiki.model.berries.entities.Berry
import com.nevi.pokemonswiki.ui.theme.MEDIUM_SPACE
import com.nevi.pokemonswiki.utils.composables.SearchAppBarWithHistory
import com.nevi.pokemonswiki.utils.composables.SnackBar

@Composable
fun BerriesListScreen(parentPaddingValues: PaddingValues, viewModel: BerriesViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = true) {
        viewModel.getHistories()
    }

    val berries = viewModel.berriesListUiState.berries.collectAsLazyPagingItems()
    val searchAppBarUiState = viewModel.searchAppBarUiState
    val histories by viewModel.histories.collectAsStateWithLifecycle()

    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val snackBarHost: @Composable () -> Unit = remember {
        {
            SnackbarHost(hostState = snackBarHostState)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize().padding(parentPaddingValues),
        snackbarHost = snackBarHost,
        topBar = {
            SearchAppBarWithHistory(
                modifier = Modifier.fillMaxWidth(),
                query = searchAppBarUiState.query,
                onQueryChange = viewModel::onQueryChange,
                onSearch = viewModel::onSearch,
                active = searchAppBarUiState.active,
                onActiveChange = viewModel::onActiveChange,
                histories = histories
            )
        }) { paddingValues ->
        BerriesListContent(items = berries, paddingValues = paddingValues)
    }

    SnackBar(
        isSnackBarVisible = viewModel.snackBarUiState.isVisible,
        snackBarMessageId = viewModel.snackBarUiState.errorMessage,
        snackBarHostState = snackBarHostState,
        onDismiss = viewModel::dismissSnackBar
    )
}

@Composable
fun BerriesListContent(items: LazyPagingItems<Berry>, paddingValues: PaddingValues) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        columns = GridCells.Fixed(count = 2)
    ) {
        items(items.itemCount) { index ->
            BerriesListItem(berry = items[index]!!)
        }
        when (val state = items.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }

            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }
    }
}

@Composable
fun BerriesListItem(berry: Berry) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                modifier = Modifier.size(100.dp, 100.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(berry.url)
                    .build(), contentDescription = ""
            )
            Spacer(modifier = Modifier.height(MEDIUM_SPACE))
            Text(text = berry.name)
        }
    }
}

fun LazyGridScope.Loading() {
    item {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}

fun LazyGridScope.Error(message: String) {
    item {
        Text(
            text = message,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}