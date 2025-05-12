package com.example.moviedb2025

import android.app.Application
import androidx.work.*
import com.example.moviedb2025.work.MovieSyncWorker
import java.util.concurrent.TimeUnit

class MovieDBApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val workRequest = PeriodicWorkRequestBuilder<MovieSyncWorker>(1, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "movie_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
