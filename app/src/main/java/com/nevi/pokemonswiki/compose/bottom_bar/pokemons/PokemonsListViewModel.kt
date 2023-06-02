package com.nevi.pokemonswiki.compose.bottom_bar.pokemons

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.room.util.query
import com.nevi.pokemonswiki.compose.base.BaseViewModel
import com.nevi.pokemonswiki.model.history.HistoryRepository
import com.nevi.pokemonswiki.model.history.entities.History
import com.nevi.pokemonswiki.model.history.entities.HistoryType
import com.nevi.pokemonswiki.model.pokemons.PokemonsRepository
import com.nevi.pokemonswiki.model.pokemons.entities.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class SearchAppBarUiState(
    val query: String = "",
    val active: Boolean = false
)

data class PokemonsListUiState(
    val pokemons: Flow<PagingData<Pokemon>> = emptyFlow()
)

@HiltViewModel
class PokemonsListViewModel @Inject constructor(
    private val pokemonsRepository: PokemonsRepository,
    private val historyRepository: HistoryRepository
) : BaseViewModel() {

    var searchAppBarUiState by mutableStateOf(SearchAppBarUiState())
        private set

    var pokemonsListUiState: PokemonsListUiState by mutableStateOf(
        PokemonsListUiState(
            pokemons = getPokemonsList(
                ""
            )
        )
    )
        private set

    private val _histories: MutableStateFlow<List<History>> = MutableStateFlow(emptyList())
    val histories = _histories.asStateFlow()

    fun getHistories() {
        viewModelScope.launch {
            historyRepository.getAllHistoryByType(HistoryType.POKEMON).collectLatest { histories ->
                _histories.emit(histories)
            }
        }
    }

    fun getPokemonsList(query: String): Flow<PagingData<Pokemon>> {
        return pokemonsRepository.getPagedPokemons(query)
            .cachedIn(viewModelScope)
    }

    fun onActiveChange(active: Boolean) {
        searchAppBarUiState = searchAppBarUiState.copy(
            active = active
        )
        if (!active && searchAppBarUiState.query == "") {
            pokemonsListUiState = pokemonsListUiState.copy(
                pokemons = getPokemonsList(searchAppBarUiState.query)
            )
        }
    }

    fun onQueryChange(query: String) {
        searchAppBarUiState = searchAppBarUiState.copy(
            query = query
        )
    }

    fun onSearch(query: String) {
        searchAppBarUiState = searchAppBarUiState.copy(
            active = false,
            query = ""
        )
        if (query != "") {
            viewModelScope.launch {
                historyRepository.addHistory(
                    History(
                        history = query,
                        dateTime = Calendar.getInstance().time,
                        type = HistoryType.POKEMON
                    )
                )
            }
            pokemonsListUiState = pokemonsListUiState.copy(
                pokemons = getPokemonsList(query)
            )
        }
    }
}