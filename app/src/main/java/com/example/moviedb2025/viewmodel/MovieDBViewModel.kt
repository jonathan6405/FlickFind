package com.example.moviedb2025.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviedb2025.database.MovieDBUIState
import com.example.moviedb2025.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import com.example.moviedb2025.models.Review
import com.example.moviedb2025.models.Video
import com.example.moviedb2025.models.VideoResponse
import com.example.moviedb2025.network.RetrofitInstance
import kotlinx.coroutines.launch
import android.util.Log

class MovieDBViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDBUIState())
    val uiState: StateFlow<MovieDBUIState> = _uiState.asStateFlow()
    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    fun setSelectedMovie(movie: Movie){
        _uiState.update { currentState ->
            currentState.copy(selectedMovie = movie)
        }
    }

    fun fetchReviews(movieId: Long, apiKey: String) {
        viewModelScope.launch {
            try {
                Log.d("MovieDBViewModel", "Fetching reviews for movieId=$movieId")
                val response = RetrofitInstance.api.getReviews(movieId, apiKey)
                _reviews.value = response.results
                Log.d("MovieDBViewModel", "Fetched ${response.results.size} reviews")
            } catch (e: Exception) {
                Log.e("MovieDBViewModel", "Error fetching reviews", e)
            }
        }
    }

    fun fetchVideos(movieId: Long, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getVideos(movieId, apiKey)
                _videos.value = response.results
            } catch (e: Exception) {
                Log.e("MovieDBViewModel", "Error fetching videos", e)
            }
        }
    }


}