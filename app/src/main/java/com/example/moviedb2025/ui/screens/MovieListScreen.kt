package com.example.moviedb2025.ui.screens

import androidx.navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.moviedb2025.models.MovieSimple
import com.example.moviedb2025.utils.Constants
import com.example.moviedb2025.viewmodel.MovieDBViewModel
import com.example.moviedb2025.BuildConfig
import androidx.compose.runtime.LaunchedEffect

@Composable
fun MovieListScreen(
    navController: NavController, // Kept for navigation
    modifier: Modifier = Modifier,
    viewModel: MovieDBViewModel = viewModel(),
    onMovieListItemClicked: (MovieSimple) -> Unit = {
        viewModel.setSelectedMovieSimple(it)
    }
) {
    val nowPlayingMovies by viewModel.nowPlaying.collectAsStateWithLifecycle()

    // Fetch movies once when screen loads
    LaunchedEffect(Unit) {
        viewModel.fetchNowPlayingMovies(BuildConfig.TMDB_API_KEY)
    }

    val navigateToDetail by viewModel.navigateToDetail.collectAsStateWithLifecycle()

    LaunchedEffect(navigateToDetail) {
        if (navigateToDetail) {
            navController.navigate(MovieDBScreen.Detail.name)
            viewModel.onNavigatedToDetail()
        }
    }


    Column(modifier = modifier.padding(16.dp)) {
        Button(onClick = { navController.navigate(MovieDBScreen.Third.name) }) {
            Text("Go to Third Screen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate(MovieDBScreen.Grid.name) }) {
            Text("Go to Grid View")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(nowPlayingMovies) { movieSimple ->
                MovieListItemCard(
                    movieSimple = movieSimple,
                    onMovieListItemClicked = onMovieListItemClicked,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun MovieListItemCard(
    movieSimple: MovieSimple,
    onMovieListItemClicked: (MovieSimple) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = { onMovieListItemClicked(movieSimple) }
    ) {
        Row {
            Box {
                AsyncImage(
                    model = Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_BASE_WIDTH + movieSimple.poster_path,
                    contentDescription = movieSimple.title,
                    modifier = Modifier
                        .width(92.dp)
                        .height(138.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = movieSimple.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = movieSimple.release_date,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = movieSimple.overview,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}
