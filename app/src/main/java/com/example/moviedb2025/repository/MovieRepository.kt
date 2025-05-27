package com.example.moviedb2025.repository

import android.content.Context
import com.example.moviedb2025.database.MovieDatabase
import com.example.moviedb2025.database.MovieSimpleEntity
import com.example.moviedb2025.models.MovieSimple
import com.example.moviedb2025.database.toEntity
import com.example.moviedb2025.database.toMovieSimple
import com.example.moviedb2025.network.RetrofitInstance

class MovieRepository(context: Context) {
    private val db = MovieDatabase.getDatabase(context)
    private val dao = db.movieDao()

    suspend fun getCachedMovies(): List<MovieSimple> =
        dao.getAllMovies().map { it.toMovieSimple() }

    suspend fun cacheMovies(movies: List<MovieSimple>) {
        dao.deleteAllMovies()
        dao.insertMovies(
            movies.mapIndexed { index, movie ->
                movie.toEntity().copy(cacheIndex = index)
            }
        )
    }


    //Fetch from API (Now Playing)
    suspend fun fetchNowPlayingMovies(apiKey: String): List<MovieSimple> {
        return RetrofitInstance.api.getNowPlayingMovies(apiKey = apiKey).results
    }
    //Fetch from API (Top Rated)
    suspend fun fetchTopRatedMovies(apiKey: String): List<MovieSimple> {
        return RetrofitInstance.api.getTopRatedMovies(apiKey = apiKey).results
    }
}
