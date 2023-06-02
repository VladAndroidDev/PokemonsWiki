package com.nevi.pokemonswiki.model.berries

import androidx.paging.PagingData
import com.nevi.pokemonswiki.model.berries.entities.Berry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultBerriesRepository @Inject constructor(private val berriesSource: BerriesSource) : BerriesRepository {

    override fun getPagedBerries(name: String): Flow<PagingData<Berry>> {
        return berriesSource.getPagedBerries(name)
    }
}