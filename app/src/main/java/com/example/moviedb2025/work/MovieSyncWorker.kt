package com.example.moviedb2025.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.moviedb2025.repository.MovieRepository
import com.example.moviedb2025.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import android.util.Log

class MovieSyncWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val repository: MovieRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                repository.refreshMovies(BuildConfig.TMDB_API_KEY)
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
