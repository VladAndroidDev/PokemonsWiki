package com.nevi.pokemonswiki.compose.bottom_bar.berries

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nevi.pokemonswiki.compose.base.BaseViewModel
import com.nevi.pokemonswiki.model.berries.BerriesRepository
import com.nevi.pokemonswiki.model.berries.entities.Berry
import com.nevi.pokemonswiki.model.history.HistoryRepository
import com.nevi.pokemonswiki.model.history.entities.History
import com.nevi.pokemonswiki.model.history.entities.HistoryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

data class BerriesListUiState(
    val berries: Flow<PagingData<Berry>> = emptyFlow()
)

@HiltViewModel
class BerriesViewModel @Inject constructor(
    private val berriesRepository: BerriesRepository,
    private val historyRepository: HistoryRepository
) :
    BaseViewModel() {

    var searchAppBarUiState by mutableStateOf(SearchAppBarUiState())
        private set

    var berriesListUiState: BerriesListUiState by mutableStateOf(
        BerriesListUiState(
            berries = getBerriesList(
                ""
            )
        )
    )
        private set

    private val _histories: MutableStateFlow<List<History>> = MutableStateFlow(emptyList())
    val histories = _histories.asStateFlow()

    fun getHistories() {
        viewModelScope.launch {
            historyRepository.getAllHistoryByType(HistoryType.BERRY).collectLatest { histories ->
                _histories.emit(histories)
            }
        }
    }

    fun getBerriesList(query: String): Flow<PagingData<Berry>> {
        return berriesRepository.getPagedBerries(query)
            .cachedIn(viewModelScope)
    }

    fun onActiveChange(active: Boolean) {
        searchAppBarUiState = searchAppBarUiState.copy(
            active = active
        )
        if (!active && searchAppBarUiState.query == "") {
            berriesListUiState = berriesListUiState.copy(
                berries = getBerriesList(searchAppBarUiState.query)
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
                        type = HistoryType.BERRY
                    )
                )
            }
            berriesListUiState = berriesListUiState.copy(
                berries = getBerriesList(query)
            )
        }
    }
}