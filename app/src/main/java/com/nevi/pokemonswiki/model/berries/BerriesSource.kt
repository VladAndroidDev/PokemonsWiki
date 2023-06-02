package com.nevi.pokemonswiki.model.berries

import androidx.paging.PagingData
import com.nevi.pokemonswiki.model.berries.entities.Berry
import kotlinx.coroutines.flow.Flow

interface BerriesSource {

    fun getPagedBerries(name: String = ""): Flow<PagingData<Berry>>

}