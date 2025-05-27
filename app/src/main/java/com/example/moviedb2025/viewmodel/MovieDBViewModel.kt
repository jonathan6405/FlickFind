package com.example.moviedb2025.viewmodel

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import com.example.moviedb2025.models.MovieSimple
import com.example.moviedb2025.repository.MovieRepository
import com.example.moviedb2025.utils.NetworkUtils

class MovieDBViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = MovieRepository(context)
        return MovieDBViewModel(repository) as T
    }
}

//Enum class to keep track of which list is selected
enum class MovieListType { NOW_PLAYING, TOP_RATED }

class MovieDBViewModel(
    private val repository: MovieRepository
) : ViewModel() {
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
    private val _isOffline = MutableStateFlow(false)
    val isOffline: StateFlow<Boolean> = _isOffline
    private val _selectedListType = MutableStateFlow(MovieListType.NOW_PLAYING)
    val selectedListType: StateFlow<MovieListType> = _selectedListType
    private var lastCachedListType: MovieListType? = null


    fun setSelectedMovie(movie: Movie){
        _uiState.update { currentState ->
            currentState.copy(selectedMovie = movie)
        }
        _navigateToDetail.value = true
    }

    fun setSelectedListType(type: MovieListType) {
        _selectedListType.value = type
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

    fun fetchNowPlayingMovies(apiKey: String, context: Context) {
        viewModelScope.launch {
            _selectedListType.value = MovieListType.NOW_PLAYING
            val online = NetworkUtils.isOnline(context)
            _isOffline.value = !online
            if (online) {
                try {
                    val movies = repository.fetchNowPlayingMovies(apiKey)
                    _nowPlaying.value = movies
                    _uiState.update { it.copy(movieList = movies) }
                    repository.cacheMovies(movies)
                    lastCachedListType = MovieListType.NOW_PLAYING
                } catch (e: Exception) {
                    // fallback to cache if API fails
                    if (lastCachedListType == MovieListType.NOW_PLAYING) {
                        val cachedMovies = repository.getCachedMovies()
                        _nowPlaying.value = cachedMovies
                        _uiState.update { it.copy(movieList = cachedMovies) }
                    } else {
                        _nowPlaying.value = emptyList()
                        _uiState.update { it.copy(movieList = emptyList()) }
                    }
                }
            } else {
                //offline - load from cache
                if (lastCachedListType == MovieListType.NOW_PLAYING) {
                    val cachedMovies = repository.getCachedMovies()
                    _nowPlaying.value = cachedMovies
                    _uiState.update { it.copy(movieList = cachedMovies) }
                } else {
                    // No cache for this list, so set blank
                    _nowPlaying.value = emptyList()
                    _uiState.update { it.copy(movieList = emptyList()) }
                }
            }
        }
    }

    val currentMovies: StateFlow<List<MovieSimple>>
        get() = nowPlaying

    fun fetchTopRatedMovies(apiKey: String, context: Context) {
        viewModelScope.launch {
            _selectedListType.value = MovieListType.TOP_RATED
            val online = NetworkUtils.isOnline(context)
            _isOffline.value = !online
            if (online) { //Same code as in fetchNowPlayingMovies, just calls top rated instead
                try {
                    val movies = repository.fetchTopRatedMovies(apiKey)
                    _nowPlaying.value = movies
                    _uiState.update { it.copy(movieList = movies) }
                    repository.cacheMovies(movies)
                    lastCachedListType = MovieListType.TOP_RATED
                } catch (e: Exception) {
                    // fallback to cache if API fails
                    if (lastCachedListType == MovieListType.TOP_RATED) {
                        val cachedMovies = repository.getCachedMovies()
                        _nowPlaying.value = cachedMovies
                        _uiState.update { it.copy(movieList = cachedMovies) }
                    } else {
                        _nowPlaying.value = emptyList()
                        _uiState.update { it.copy(movieList = emptyList()) }
                    }
                }
            } else {
                //offline - load from cache
                if (lastCachedListType == MovieListType.TOP_RATED) {
                    val cachedMovies = repository.getCachedMovies()
                    _nowPlaying.value = cachedMovies
                    _uiState.update { it.copy(movieList = cachedMovies) }
                } else {
                    //No cache for this list, so set blank
                    _nowPlaying.value = emptyList()
                    _uiState.update { it.copy(movieList = emptyList()) }
                }
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