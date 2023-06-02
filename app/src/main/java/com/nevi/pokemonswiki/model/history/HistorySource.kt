package com.nevi.pokemonswiki.model.history

import com.nevi.pokemonswiki.model.history.entities.History
import com.nevi.pokemonswiki.model.history.entities.HistoryType
import kotlinx.coroutines.flow.Flow

interface HistorySource {

    suspend fun addHistory(history: History)

    fun getAllHistoryByType(historyType: HistoryType): Flow<List<History>>

}