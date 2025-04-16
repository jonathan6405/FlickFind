package com.example.moviedb2025.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviedb2025.models.Movie
import com.example.moviedb2025.models.Genre
import com.example.moviedb2025.utils.Constants
import android.content.Intent
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun MovieDetailScreen(movie: Movie,
                      modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column {
        Box {
            AsyncImage(
                model = Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_BASE_WIDTH + movie.backdropPath,
                contentDescription = movie.title,
                modifier = Modifier, //complete weight
                contentScale = ContentScale.Crop
            )
        }

        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = movie.releaseDate,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = movie.overview,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis

        )
        Spacer(modifier = Modifier.size(8.dp))

        GenreList(genres = movie.genres)
        Spacer(modifier = Modifier.size(16.dp))

        Button(onClick = {
            val intent = Intent(Intent.ACTION_VIEW, movie.homepage.toUri())
            context.startActivity(intent)
        }) {
            Text(text = "Go to Homepage")
        }

        Button(onClick = {
            val imdbUrl = "https://www.imdb.com/title/${movie.imdb_id}"
            val intent = Intent(Intent.ACTION_VIEW, imdbUrl.toUri())
            intent.setPackage("com.imdb.mobile") //tells Android to prefer the IMDb app
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                // IMDb app not installed, open in browser instead
                val fallbackIntent = Intent(Intent.ACTION_VIEW, imdbUrl.toUri())
                context.startActivity(fallbackIntent)
            }
        }) {
            Text("View on IMDb")
        }
    }
}

@Composable
fun GenreList(genres: List<Genre>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Genres:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(4.dp))
        genres.forEach { genre ->
            Text(text = "• ${genre.name}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}