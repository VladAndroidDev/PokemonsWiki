package com.nevi.pokemonswiki.model.pokemons.entities

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl:String,
    val height: Double,
    val weight: Double,
    val stats: List<Stat>
)
