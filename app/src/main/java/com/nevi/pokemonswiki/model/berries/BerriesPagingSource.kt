package com.nevi.pokemonswiki.model.berries

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nevi.pokemonswiki.model.berries.entities.Berry

data class BerriesListResponse(val next: String?, val result: List<Berry>)

typealias BerriesLoader = suspend (offset: Int, limit: Int) -> BerriesListResponse

class BerriesPagingSource(private val loader: BerriesLoader) : PagingSource<Int, Berry>() {

    override fun getRefreshKey(state: PagingState<Int, Berry>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Berry> {
        val offset = params.key ?: 0
        val loadSize = params.loadSize
        return try {
            val (next, result) = loader.invoke(offset, loadSize)

            LoadResult.Page(
                data = result,
                prevKey = if (offset == 0) null else offset - 1,
                nextKey = if (next == null) null else offset + loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}