package com.nevi.pokemonswiki.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nevi.pokemonswiki.model.history.room.HistoryDao
import com.nevi.pokemonswiki.model.room.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    companion object {

        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "pokemon.db"
            ).build()
        }

        @Provides
        @Singleton
        fun provideHistoryDao(appDatabase: AppDatabase): HistoryDao {
            return appDatabase.getHistoryDao()
        }
    }
}