package com.nevi.pokemonswiki.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nevi.pokemonswiki.model.history.room.HistoryDao
import com.nevi.pokemonswiki.model.history.room.entities.HistoryDbEntity
import com.nevi.pokemonswiki.model.room.converters.DateConverter
import com.nevi.pokemonswiki.model.room.converters.HistoryTypeConverter

@Database(entities = [HistoryDbEntity::class], version = 1)
@TypeConverters(DateConverter::class, HistoryTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getHistoryDao(): HistoryDao

}