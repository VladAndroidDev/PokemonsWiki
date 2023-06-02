package com.nevi.pokemonswiki.compose.pokemon_detail

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nevi.pokemonswiki.R
import com.nevi.pokemonswiki.ui.theme.MEDIUM_PADDING
import com.nevi.pokemonswiki.utils.composables.SnackBar

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    navigateUp: () -> Unit,
    viewModel: PokemonDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = pokemonId) {
        viewModel.getPokemonById(pokemonId)
    }

    val snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val snackBarHost: @Composable () -> Unit = remember {
        {
            SnackbarHost(hostState = snackBarHostState)
        }
    }

    if (viewModel.pokemonsUiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
        }
    } else if (viewModel.pokemonsUiState.isError) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Try again later",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    } else {
        Scaffold(snackbarHost = snackBarHost, topBar = {
            PokemonDetailTopAppBar(
                title = viewModel.pokemonsUiState.name,
                navigationUpClicked = navigateUp
            )
        }) {
            PokemonDetailContent(
                imageUrl = viewModel.pokemonsUiState.imageUrl,
                weight = viewModel.pokemonsUiState.weight,
                height = viewModel.pokemonsUiState.height,
                stats = viewModel.pokemonsUiState.stats
            )
        }
    }

    SnackBar(
        isSnackBarVisible = viewModel.snackBarUiState.isVisible,
        snackBarMessageId = viewModel.snackBarUiState.errorMessage,
        snackBarHostState = snackBarHostState,
        onDismiss = viewModel::dismissSnackBar
    )
}

@Composable
fun PokemonDetailContent(
    imageUrl: String,
    weight: Double,
    height: Double,
    stats: List<StatUiState>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            //.verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier.size(250.dp),
                model = ImageRequest.Builder(LocalContext.current).data(imageUrl).build(),
                contentDescription = ""
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "$weight " + stringResource(id = R.string.kg))
                Text(text = "$height " + stringResource(id = R.string.metres))
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = R.string.base_stats),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
//        NonScrollableList(items = stats, content = { StatListItem(statUiState = it) })
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                //.scrollEnabled(enabled = false)
                .padding(16.dp),
            columns = GridCells.Fixed(count = 2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(stats) { stat ->
                StatListItem(statUiState = stat)
            }
        }
    }
}


//fun Modifier.scrollEnabled(
//    enabled: Boolean,
//) = nestedScroll(
//    connection = object : NestedScrollConnection {
//        override fun onPreScroll(
//            available: Offset,
//            source: NestedScrollSource
//        ): Offset = if (enabled) Offset.Zero else available
//    }
//)

@Composable
fun StatListItem(statUiState: StatUiState) {
    var animState by remember {
        mutableStateOf(false)
    }
    val value: Int by animateIntAsState(
        targetValue = if (animState) statUiState.value.toInt() else 0,
        animationSpec = tween(durationMillis = 1000)
    )

    Card(modifier = Modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp),
                    progress = value.toFloat() / 255
                )
                Text(modifier = Modifier, text = value.toString())
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = statUiState.name)
        }
    }
    LaunchedEffect(key1 = statUiState.value) {
        animState = true
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailTopAppBar(title: String, navigationUpClicked: () -> Unit) {
    TopAppBar(title = {
        Text(text = title)
    },
        navigationIcon = {
            IconButton(onClick = navigationUpClicked) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        })
}

@Preview(showBackground = true)
@Composable
fun StatListItemPreview() {
    StatListItem(statUiState = StatUiState("power", 100.0))
}
