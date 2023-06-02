package com.nevi.pokemonswiki.model.base

import com.nevi.pokemonswiki.utils.Async
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseSource {

    private suspend fun <T> FlowCollector<Async<T>>.safeCall(
        call: suspend () -> T
    ) {
        emit(Async.Loading)
        val result = call.invoke()
        emit(Async.Success(result))
    }

    fun <T> safeFlow(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        call: suspend () -> T
    ): Flow<Async<T>> {
        return flow {
            safeCall(call = call)
        }.flowOn(dispatcher).catch { e -> emit(Async.Error(e)) }
    }
}