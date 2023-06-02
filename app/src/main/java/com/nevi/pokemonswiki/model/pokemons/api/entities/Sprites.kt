package com.nevi.pokemonswiki.model.pokemons.api.entities

import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("front_default") val frontDefault: String
)