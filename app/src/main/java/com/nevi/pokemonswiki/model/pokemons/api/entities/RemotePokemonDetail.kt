package com.nevi.pokemonswiki.model.pokemons.api.entities

import com.nevi.pokemonswiki.model.pokemons.api.ParsePokemonImageUrl
import com.nevi.pokemonswiki.model.pokemons.entities.PokemonDetail

data class RemotePokemonDetail(
    val id: Int,
    val name: String,
    val height: Double,
    val weight: Double,
    val stats: List<RemoteStats>,
    val sprites: Sprites,
) {

    fun toPokemonDetail(): PokemonDetail {
        return PokemonDetail(
            id,
            name,
            sprites.frontDefault,
            height,
            weight,
            stats.map(RemoteStats::toStat)
        )
    }
}
