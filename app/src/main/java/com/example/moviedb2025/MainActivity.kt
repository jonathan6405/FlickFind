package com.example.moviedb2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.moviedb2025.ui.theme.MovieDB2025Theme
import com.example.moviedb2025.ui.screens.MovieDbApp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.moviedb2025.work.ShowcaseWorker


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val request = OneTimeWorkRequestBuilder<ShowcaseWorker>().build()
        WorkManager.getInstance(this).enqueue(request)
        enableEdgeToEdge()
        setContent {
            MovieDB2025Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MovieDbApp()
                }
            }
        }
    }
}

