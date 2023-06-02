package com.nevi.pokemonswiki.model.pokemons.api

data class ParseImageUrlResult(
    val id: Int,
    val imageUrl: String
)

object ParsePokemonImageUrl {

    private fun extractId(url: String) = url.substringAfter("pokemon").replace("/", "").toInt()

    operator fun invoke(url: String): ParseImageUrlResult {
        val id = extractId(url)
        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
        return ParseImageUrlResult(id, imageUrl)
    }
}