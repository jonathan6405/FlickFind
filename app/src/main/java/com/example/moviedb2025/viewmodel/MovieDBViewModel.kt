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
import com.example.moviedb2025.network.RetrofitInstance
import kotlinx.coroutines.launch
import android.util.Log
import com.example.moviedb2025.models.MovieSimple

class MovieDBViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDBUIState())
    val uiState: StateFlow<MovieDBUIState> = _uiState.asStateFlow()
    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos
    private val _nowPlaying = MutableStateFlow<List<MovieSimple>>(emptyList())
    val nowPlaying: StateFlow<List<MovieSimple>> = _nowPlaying
    private val _selectedMovie = MutableStateFlow<MovieSimple?>(null)
    val selectedMovie: StateFlow<MovieSimple?> = _selectedMovie
    private val _navigateToDetail = MutableStateFlow(false)
    val navigateToDetail: StateFlow<Boolean> = _navigateToDetail

    fun setSelectedMovie(movie: Movie){
        _uiState.update { currentState ->
            currentState.copy(selectedMovie = movie)
        }
        _navigateToDetail.value = true
    }

    fun setSelectedMovieSimple(movie: MovieSimple) {
        _uiState.update { it.copy(selectedMovieSimple = movie) }
        _navigateToDetail.value = true
    }

    fun onNavigatedToDetail() {
        _navigateToDetail.value = false
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

    fun fetchNowPlayingMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getNowPlayingMovies(apiKey = apiKey)
                _nowPlaying.value = response.results
                _uiState.update { it.copy(movieList = response.results) }
            } catch (e: Exception) {
                Log.e("MovieDBViewModel", "Error fetching now playing movies", e)
            }
        }
    }

    fun fetchMovieDetails(movieId: Long, apiKey: String) {
        viewModelScope.launch {
            try {
                val movieDetails = RetrofitInstance.api.getMovieDetails(movieId,apiKey = apiKey)
                _uiState.update { currentState ->
                    currentState.copy(selectedMovie = movieDetails)
                }
            } catch (e: Exception) {
                Log.e("MovieDBViewModel", "Error fetching movie details", e)
            }
        }
    }



}