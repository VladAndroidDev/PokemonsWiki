package com.nevi.pokemonswiki.utils

sealed class Async<out T> {

    data class Success<out T>(val data: T) : Async<T>()

    data class Error(val e: Throwable) : Async<Nothing>()

    object Loading : Async<Nothing>()

}
