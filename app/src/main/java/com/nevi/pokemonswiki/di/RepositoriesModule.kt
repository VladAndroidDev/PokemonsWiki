package com.nevi.pokemonswiki.di

import com.nevi.pokemonswiki.model.berries.BerriesRepository
import com.nevi.pokemonswiki.model.berries.DefaultBerriesRepository
import com.nevi.pokemonswiki.model.history.DefaultHistoryRepository
import com.nevi.pokemonswiki.model.history.HistoryRepository
import com.nevi.pokemonswiki.model.pokemons.DefaultPokemonsRepository
import com.nevi.pokemonswiki.model.pokemons.PokemonsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun bindPokemonsRepository(defaultPokemonsRepository: DefaultPokemonsRepository): PokemonsRepository

    @Binds
    @Singleton
    abstract fun bindBerriesRepository(defaultBerriesRepository: DefaultBerriesRepository): BerriesRepository

    @Binds
    @Singleton
    abstract fun bindHistoryRepository(defaultHistoryRepository: DefaultHistoryRepository): HistoryRepository
}