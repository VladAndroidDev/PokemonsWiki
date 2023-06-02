package com.nevi.pokemonswiki.model.berries.api

import com.nevi.pokemonswiki.model.base.entities.PagingResponse
import com.nevi.pokemonswiki.model.berries.api.entities.RemoteBerry
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BerryApi {

    @GET("berry/")
    suspend fun getBerries(
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?
    ): PagingResponse<RemoteBerry>

}