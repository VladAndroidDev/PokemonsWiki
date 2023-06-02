package com.nevi.pokemonswiki.model.berries.api.entities

import com.nevi.pokemonswiki.model.berries.entities.Berry

data class RemoteBerry(
    val name: String,
    val url: String
) {

    private fun extractId(url: String) = url.substringAfter("berry").replace("/", "").toInt()

    fun toBerry(): Berry {
        val id = extractId(url)

        val imageUrl =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/berries/$name-berry.png"
        return Berry(id, name, imageUrl)
    }

}