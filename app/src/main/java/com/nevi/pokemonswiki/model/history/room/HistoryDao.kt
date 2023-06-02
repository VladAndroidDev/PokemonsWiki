package com.nevi.pokemonswiki.model.history.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.nevi.pokemonswiki.model.history.entities.HistoryType
import com.nevi.pokemonswiki.model.history.room.entities.HistoryDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history WHERE type=:historyType ORDER BY date_time DESC")
    fun getAllHistoryByType(historyType: HistoryType): Flow<List<HistoryDbEntity>>

    @Insert
    suspend fun addHistory(historyDbEntity: HistoryDbEntity)
}