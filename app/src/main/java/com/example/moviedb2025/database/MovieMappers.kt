package com.example.moviedb2025.database

import com.example.moviedb2025.models.MovieSimple

fun MovieEntity.toMovieSimple(): MovieSimple {
    return MovieSimple(
        id = id,
        title = title,
        overview = overview,
        release_date = releaseDate,
        poster_path = posterPath,
        backdrop_path = backdropPath
    )
}

fun MovieSimple.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        releaseDate = release_date,
        posterPath = poster_path,
        backdropPath = backdrop_path
    )
}
