package com.example.moviedb2025.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY cacheIndex ASC")
    suspend fun getAllMovies(): List<MovieSimpleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieSimpleEntity>)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}
