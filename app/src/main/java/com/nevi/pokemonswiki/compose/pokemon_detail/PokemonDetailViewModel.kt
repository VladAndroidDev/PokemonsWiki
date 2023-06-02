package com.nevi.pokemonswiki.compose.pokemon_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.nevi.pokemonswiki.R
import com.nevi.pokemonswiki.compose.base.BaseViewModel
import com.nevi.pokemonswiki.model.pokemons.PokemonsRepository
import com.nevi.pokemonswiki.utils.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

data class PokemonDetailUiState(
    val name: String = "",
    val imageUrl: String = "",
    val weight: Double = 0.0,
    val height: Double = 0.0,
    val stats: List<StatUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

data class StatUiState(
    val name: String,
    val value: Double
)

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(private val pokemonsRepository: PokemonsRepository) :
    BaseViewModel() {

    var pokemonsUiState: PokemonDetailUiState by mutableStateOf(PokemonDetailUiState())
        private set

    fun getPokemonById(id: Int) {
        viewModelScope.launch {
            pokemonsRepository.getPokemonDetailById(id).collect { result ->
                when (result) {
                    is Async.Success -> {
                        val data = result.data
                        pokemonsUiState = pokemonsUiState.copy(
                            name = data.name,
                            imageUrl = data.imageUrl,
                            weight = data.weight,
                            height = data.height,
                            stats = data.stats.map { stat ->
                                StatUiState(stat.name, stat.baseStat)
                            },
                            isLoading = false,
                            isError = false
                        )
                    }

                    is Async.Error -> {
                        handleException(result.e as Exception)
                        pokemonsUiState = pokemonsUiState.copy(
                            isError = true,
                            isLoading = false
                        )
                    }

                    is Async.Loading -> {
                        pokemonsUiState = pokemonsUiState.copy(
                            isLoading = true,
                            isError = false
                        )
                    }
                }
            }
        }
    }

    private fun handleException(e: Exception) {
        when (e) {
            is IOException -> {
                showSnackBar(R.string.bad_connetction)
            }

            else -> {
                showSnackBar(R.string.something_was_wrong)
            }
        }
    }

}