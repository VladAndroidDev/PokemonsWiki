package com.nevi.pokemonswiki.model.pokemons.api.entities

import com.google.gson.annotations.SerializedName
import com.nevi.pokemonswiki.model.pokemons.entities.Stat

data class RemoteStats(
    @SerializedName("base_stat")
    val baseStat: Double,
    val stat: RemoteStat
) {

    fun toStat(): Stat {
        return Stat(stat.name, baseStat)
    }
}
