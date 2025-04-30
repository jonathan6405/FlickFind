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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviedb2025.models.Review
import com.example.moviedb2025.viewmodel.MovieDBViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import com.example.moviedb2025.BuildConfig
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember




@Composable
fun MovieDetailScreen(movie: Movie,
                      modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val viewModel: MovieDBViewModel = viewModel()
    val reviews by viewModel.reviews.collectAsStateWithLifecycle()


    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()) //Enable vertical scrolling for details scree
    ) {
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

        Button(onClick = {
            viewModel.fetchReviews(movie.id, BuildConfig.TMDB_API_KEY)
        }) {
            Text("View Reviews")
        }

        Spacer(modifier = Modifier.size(16.dp))


        LazyRow {
            items(reviews) { review: Review ->
                ReviewCard(review = review)
            }
        }

        Button(onClick = {
            viewModel.fetchVideos(movie.id, BuildConfig.TMDB_API_KEY)
        }) {
            Text("Watch Trailer")
        }

        val videos by viewModel.videos.collectAsStateWithLifecycle()

        if (videos.isNotEmpty()) {
            // Take the first YouTube video
            val firstVideo = videos.firstOrNull { it.site == "YouTube" }

            firstVideo?.let { video ->
                val youtubeUrl = "https://www.youtube.com/watch?v=${video.key}"
                VideoPlayer(videoUrl = "https://onlinetestcase.com/wp-content/uploads/2023/06/1MB.mp4")
            }
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

@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(300.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = review.author, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = review.content, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun VideoPlayer(videoUrl: String) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(videoUrl)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(factory = {
        PlayerView(it).apply {
            player = exoPlayer
        }
    })
}