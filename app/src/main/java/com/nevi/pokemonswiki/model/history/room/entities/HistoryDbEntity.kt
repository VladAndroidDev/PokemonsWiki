package com.nevi.pokemonswiki.model.history.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nevi.pokemonswiki.model.history.entities.History
import com.nevi.pokemonswiki.model.history.entities.HistoryType
import java.util.Date

@Entity(tableName = "history")
data class HistoryDbEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int,
    val history: String,
    @ColumnInfo(name = "date_time")
    val dateTime: Date,
    val type: HistoryType
) {

    constructor(history: History) : this(
        history.id,
        history.history,
        history.dateTime,
        history.type
    ) {

    }

    fun toHistory(): History {
        return History(id, history, dateTime, type)
    }

}