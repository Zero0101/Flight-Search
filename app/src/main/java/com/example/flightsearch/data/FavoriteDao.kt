package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addToFavorite(flight: Favorite)

    @Delete
    suspend fun removeFromFavorite(flight: Favorite)

    @Query("SELECT * from favorite")
    fun getFavorites(): Flow<List<Favorite>>
}