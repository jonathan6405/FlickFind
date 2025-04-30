package com.example.moviedb2025.models

data class ReviewResponse(
    val results: List<Review>
)

data class Review(
    val author: String,
    val content: String
)
