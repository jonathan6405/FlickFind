package com.example.moviedb2025.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviedb2025.models.MovieSimple

@Entity(tableName = "movies")
data class MovieSimpleEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val poster_path: String,
    val backdrop_path: String,
    val release_date: String,
    val overview: String,
    val cacheIndex: Int = 0
)

// Helper function to convert between MovieSimple and MovieSimpleEntity
fun MovieSimple.toEntity(): MovieSimpleEntity =
    MovieSimpleEntity(id, title, poster_path, backdrop_path, release_date, overview)

fun MovieSimpleEntity.toMovieSimple(): MovieSimple =
    MovieSimple(id, title, poster_path, backdrop_path, release_date, overview)
