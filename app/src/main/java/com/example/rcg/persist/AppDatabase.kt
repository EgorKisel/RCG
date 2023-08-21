package com.example.rcg.persist

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rcg.persist.model.GameEntity

@Database(entities = [GameEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao
}