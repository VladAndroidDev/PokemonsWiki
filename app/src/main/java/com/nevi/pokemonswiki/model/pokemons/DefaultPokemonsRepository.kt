package com.nevi.pokemonswiki.model.pokemons

import androidx.paging.PagingData
import com.nevi.pokemonswiki.model.pokemons.entities.Pokemon
import com.nevi.pokemonswiki.model.pokemons.entities.PokemonDetail
import com.nevi.pokemonswiki.utils.Async
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPokemonsRepository @Inject constructor(private val pokemonsSource: PokemonsSource) :
    PokemonsRepository {

    override fun getPagedPokemons(name: String): Flow<PagingData<Pokemon>> {
        return pokemonsSource.getPagedPokemons(name)
    }

    override fun getPokemonDetailById(id: Int): Flow<Async<PokemonDetail>> {
        return pokemonsSource.getPokemonDetailById(id)
    }
}