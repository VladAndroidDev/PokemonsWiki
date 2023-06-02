package com.nevi.pokemonswiki.model.pokemons.api.entities

import com.nevi.pokemonswiki.model.pokemons.api.ParsePokemonImageUrl
import com.nevi.pokemonswiki.model.pokemons.entities.Pokemon

data class RemotePokemon(
    val name: String,
    val url: String
) {

    fun toPokemon(): Pokemon {
        val (id, imageUrl) = ParsePokemonImageUrl(url)
        return Pokemon(id, name, imageUrl)
    }
}
