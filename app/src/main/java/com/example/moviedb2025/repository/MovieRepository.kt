package com.example.moviedb2025.repository

import android.content.Context
import com.example.moviedb2025.database.AppDatabase
import com.example.moviedb2025.models.MovieSimple
import com.example.moviedb2025.database.toMovieSimple
import com.example.moviedb2025.database.toEntity
import com.example.moviedb2025.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(context: Context) {

    private val movieDao = AppDatabase.getDatabase(context).movieDao()

    suspend fun getCachedMovies(): List<MovieSimple> = withContext(Dispatchers.IO) {
        movieDao.getAllMovies().map { it.toMovieSimple() }
    }

    suspend fun refreshMovies(apiKey: String) = withContext(Dispatchers.IO) {
        val response = RetrofitInstance.api.getNowPlayingMovies(apiKey = apiKey)
        val entities = response.results.map { it.toEntity() }

        movieDao.clearAll()
        movieDao.insertAll(entities)
    }
}
