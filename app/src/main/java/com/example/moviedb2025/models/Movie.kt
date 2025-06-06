package com.example.moviedb2025.models

data class Movie(
    var id: Long = 0L,
    var title: String,
    var poster_path: String,
    var backdrop_path: String,
    var release_date: String,
    var overview: String,
    val genres: List<Genre>,
    val homepage: String,
    val imdb_id: String
)
