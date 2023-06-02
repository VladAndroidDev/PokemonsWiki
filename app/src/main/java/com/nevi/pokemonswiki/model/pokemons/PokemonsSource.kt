package com.nevi.pokemonswiki.model.pokemons

import androidx.paging.PagingData
import com.nevi.pokemonswiki.model.pokemons.entities.Pokemon
import com.nevi.pokemonswiki.model.pokemons.entities.PokemonDetail
import com.nevi.pokemonswiki.utils.Async
import kotlinx.coroutines.flow.Flow

interface PokemonsSource {

    fun getPagedPokemons(name: String = ""): Flow<PagingData<Pokemon>>

    fun getPokemonDetailById(id: Int): Flow<Async<PokemonDetail>>

}