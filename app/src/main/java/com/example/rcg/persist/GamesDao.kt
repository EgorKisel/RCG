package com.example.rcg.persist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rcg.persist.model.GameEntity

@Dao
interface GamesDao {

    @Query("SELECT * FROM games WHERE category_id = 0 order by added DESC")
    fun getMostAnticipated(): List<GameEntity>

    @Query("SELECT * FROM games WHERE category_id = 1 order by released DESC")
    fun getLatestReleases(): List<GameEntity>

    @Query("SELECT * FROM games WHERE category_id = 2 order by rating DESC")
    fun getRated(): List<GameEntity>

    @Insert
    fun insertAll(games: List<GameEntity>)

    @Query("DELETE FROM games WHERE category_id = :categoryId")
    fun clear(categoryId: Int)
}