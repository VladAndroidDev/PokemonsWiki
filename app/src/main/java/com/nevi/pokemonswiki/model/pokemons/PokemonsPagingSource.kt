package com.nevi.pokemonswiki.model.pokemons

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nevi.pokemonswiki.model.pokemons.entities.Pokemon

data class PokemonsListResponse(val next: String?, val data: List<Pokemon>)

typealias PokemonsLoader = suspend (offset: Int, limit: Int) -> PokemonsListResponse

class PokemonsPagingSource(private val loader: PokemonsLoader) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val offset = params.key ?: 0
        val loadSize = params.loadSize
        return try {
            val (next, data) = loader.invoke(offset, loadSize)

            return LoadResult.Page(
                data = data,
                prevKey = if (offset == 0) null else offset - 1,
                nextKey = if (next == null) null else offset + loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}