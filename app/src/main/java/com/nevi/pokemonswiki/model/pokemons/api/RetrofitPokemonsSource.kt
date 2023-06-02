package com.nevi.pokemonswiki.model.pokemons.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nevi.pokemonswiki.Consts
import com.nevi.pokemonswiki.model.base.BaseSource
import com.nevi.pokemonswiki.model.pokemons.PokemonsListResponse
import com.nevi.pokemonswiki.model.pokemons.PokemonsLoader
import com.nevi.pokemonswiki.model.pokemons.PokemonsPagingSource
import com.nevi.pokemonswiki.model.pokemons.PokemonsSource
import com.nevi.pokemonswiki.model.pokemons.api.entities.RemotePokemon
import com.nevi.pokemonswiki.model.pokemons.entities.Pokemon
import com.nevi.pokemonswiki.model.pokemons.entities.PokemonDetail
import com.nevi.pokemonswiki.utils.Async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitPokemonsSource @Inject constructor(private val pokemonsApi: PokemonApi) :
    BaseSource(),
    PokemonsSource {

    private val dispatcher = Dispatchers.IO

    override fun getPagedPokemons(name: String): Flow<PagingData<Pokemon>> {
        val loader: PokemonsLoader = { offset: Int, limit: Int ->
            getPokemonsResponse(offset, limit, name)
        }

        return Pager(
            config = PagingConfig(
                pageSize = Consts.PAGE_SIZE,
                initialLoadSize = Consts.PAGE_SIZE
            ),
            pagingSourceFactory = {
                PokemonsPagingSource(loader)
            }
        ).flow
    }

    override fun getPokemonDetailById(id: Int): Flow<Async<PokemonDetail>> {
        return safeFlow {
            pokemonsApi.getPokemonDetailById(id).toPokemonDetail()
        }
    }

    private suspend fun getPokemonsResponse(offset: Int, limit: Int, name: String) =
        withContext(dispatcher) {
            val response = pokemonsApi.getPokemons(offset, limit)

            val pokemons = response.results.map(RemotePokemon::toPokemon).filter { pokemon ->
                pokemon.name.contains(name)
            }

            return@withContext PokemonsListResponse(
                response.next,
                pokemons
            )
        }
}