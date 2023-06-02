package com.nevi.pokemonswiki.model.history

import com.nevi.pokemonswiki.model.history.entities.History
import com.nevi.pokemonswiki.model.history.entities.HistoryType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultHistoryRepository @Inject constructor(private val historySource: HistorySource) :
    HistoryRepository {

    private val dispatcher = Dispatchers.IO

    override suspend fun addHistory(history: History) = withContext(dispatcher) {
        historySource.addHistory(history)
    }

    override fun getAllHistoryByType(historyType: HistoryType): Flow<List<History>> {
        return historySource.getAllHistoryByType(historyType)
    }
}