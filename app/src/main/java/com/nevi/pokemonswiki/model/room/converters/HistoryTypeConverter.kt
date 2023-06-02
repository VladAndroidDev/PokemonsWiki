package com.nevi.pokemonswiki.model.room.converters

import androidx.room.TypeConverter
import com.nevi.pokemonswiki.model.history.entities.HistoryType

class HistoryTypeConverter {

    @TypeConverter
    fun toHealth(value: Int) = enumValues<HistoryType>()[value]

    @TypeConverter
    fun fromHealth(value: HistoryType) = value.ordinal

}