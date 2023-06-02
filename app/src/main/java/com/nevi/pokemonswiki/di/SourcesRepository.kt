package com.nevi.pokemonswiki.di

import com.nevi.pokemonswiki.model.berries.BerriesSource
import com.nevi.pokemonswiki.model.berries.api.RetrofitBerriesSource
import com.nevi.pokemonswiki.model.history.HistorySource
import com.nevi.pokemonswiki.model.history.room.RoomHistorySource
import com.nevi.pokemonswiki.model.pokemons.PokemonsSource
import com.nevi.pokemonswiki.model.pokemons.api.RetrofitPokemonsSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesRepository {

    @Binds
    @Singleton
    abstract fun bindPokemonsSource(retrofitPokemonsSource: RetrofitPokemonsSource): PokemonsSource

    @Binds
    @Singleton
    abstract fun bindBerriesSource(retrofitBerriesSource: RetrofitBerriesSource): BerriesSource

    @Binds
    @Singleton
    abstract fun bindHistoriesSource(roomHistorySource: RoomHistorySource): HistorySource
}