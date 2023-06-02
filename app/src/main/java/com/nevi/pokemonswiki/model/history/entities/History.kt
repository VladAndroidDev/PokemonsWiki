package com.nevi.pokemonswiki.model.history.entities

import java.util.Date

data class History(
    val id: Int = 0,
    val history: String,
    val dateTime: Date,
    val type: HistoryType
)

enum class HistoryType {
    POKEMON,
    BERRY
}
