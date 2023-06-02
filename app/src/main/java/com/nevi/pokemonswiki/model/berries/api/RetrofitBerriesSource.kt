package com.nevi.pokemonswiki.model.berries.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nevi.pokemonswiki.Consts.PAGE_SIZE
import com.nevi.pokemonswiki.model.base.entities.PagingResponse
import com.nevi.pokemonswiki.model.berries.BerriesListResponse
import com.nevi.pokemonswiki.model.berries.BerriesLoader
import com.nevi.pokemonswiki.model.berries.BerriesPagingSource
import com.nevi.pokemonswiki.model.berries.BerriesSource
import com.nevi.pokemonswiki.model.berries.api.entities.RemoteBerry
import com.nevi.pokemonswiki.model.berries.entities.Berry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitBerriesSource @Inject constructor(private val berryApi: BerryApi) :
    BerriesSource {

    private val dispatcher = Dispatchers.IO

    override fun getPagedBerries(name: String): Flow<PagingData<Berry>> {
        val loader: BerriesLoader = { offset: Int, limit: Int ->
            getBerriesResponse(offset, limit, name)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                BerriesPagingSource(loader)
            }
        ).flow
    }

    private suspend fun getBerriesResponse(offset: Int, limit: Int, name: String) =
        withContext(dispatcher) {
            val response: PagingResponse<RemoteBerry> = berryApi.getBerries(offset, limit)

            val berries = response.results.map(RemoteBerry::toBerry).filter { berry ->
                berry.name.contains(name)
            }

            return@withContext BerriesListResponse(
                response.next,
                berries
            )
        }
}