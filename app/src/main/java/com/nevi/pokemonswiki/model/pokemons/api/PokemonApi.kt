package com.nevi.pokemonswiki.model.pokemons.api

import com.nevi.pokemonswiki.model.base.entities.PagingResponse
import com.nevi.pokemonswiki.model.pokemons.api.entities.RemotePokemon
import com.nevi.pokemonswiki.model.pokemons.api.entities.RemotePokemonDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun getPokemons(
        @Query("offset") offset: Int?,
        @Query("limit") limit: Int?
    ): PagingResponse<RemotePokemon>

    @GET("pokemon/{id}")
    suspend fun getPokemonDetailById(
        @Path("id") id: Int
    ): RemotePokemonDetail

}