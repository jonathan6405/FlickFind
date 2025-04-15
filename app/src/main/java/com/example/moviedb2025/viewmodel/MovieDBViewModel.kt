package com.example.moviedb2025.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviedb2025.database.MovieDBUIState
import com.example.moviedb2025.models.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MovieDBViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDBUIState())
    val uiState: StateFlow<MovieDBUIState> = _uiState.asStateFlow()

    fun setSelectedMovie(movie: Movie){
        _uiState.update { currentState ->
            currentState.copy(selectedMovie = movie)
        }
    }
}