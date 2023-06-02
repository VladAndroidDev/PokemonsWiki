package com.nevi.pokemonswiki.model.history.room

import com.nevi.pokemonswiki.model.history.HistorySource
import com.nevi.pokemonswiki.model.history.entities.History
import com.nevi.pokemonswiki.model.history.entities.HistoryType
import com.nevi.pokemonswiki.model.history.room.entities.HistoryDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomHistorySource @Inject constructor(private val historyDao: HistoryDao) : HistorySource {

    private val dispatcher = Dispatchers.IO

    override suspend fun addHistory(history: History) = withContext(dispatcher) {
        historyDao.addHistory(HistoryDbEntity(history))
    }

    override fun getAllHistoryByType(historyType: HistoryType): Flow<List<History>> {
        return historyDao.getAllHistoryByType(historyType).map {
            it.map(HistoryDbEntity::toHistory)
        }.flowOn(dispatcher)
    }
}