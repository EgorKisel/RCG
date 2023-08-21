package com.example.rcg.persist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(
    @ColumnInfo(name = "externalId") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "category_id") val categoryId: Int,
    @ColumnInfo(name = "released") val released: String,
    @ColumnInfo(name = "added") val added: Long,
    @ColumnInfo(name = "rating") val rating: Float
) {
    @PrimaryKey(autoGenerate = true)
    var pk: Long = 0
}
