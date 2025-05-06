package com.example.moviedb2025.database

import com.example.moviedb2025.models.MovieSimple
import com.example.moviedb2025.models.Movie

data class MovieDBUIState(
    val movieList: List<MovieSimple> = emptyList(),
    val selectedMovie: Movie? = null,
    val selectedMovieSimple: MovieSimple? = null
)
