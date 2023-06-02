package com.nevi.pokemonswiki.model.base.entities

data class PagingResponse<R>(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<R>
)
