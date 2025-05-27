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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.moviedb2025.utils.ConnectionState
import com.example.moviedb2025.utils.observeConnectivity
import com.example.moviedb2025.viewmodel.MovieDBViewModelFactory

@Composable
fun MovieListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MovieDBViewModel = viewModel(
        factory = MovieDBViewModelFactory(LocalContext.current.applicationContext)
    ),
    onMovieListItemClicked: (MovieSimple) -> Unit = {
        viewModel.setSelectedMovieSimple(it)
    }
) {
    val nowPlayingMovies by viewModel.nowPlaying.collectAsStateWithLifecycle()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val movieList = uiState.movieList

    // Fetch movies once when screen loads
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchNowPlayingMovies(BuildConfig.TMDB_API_KEY, context)
    }

    //Observe connection status and trigger a reload if we go from offline to online
    val connectionState = remember { mutableStateOf<ConnectionState?>(null) }
    LaunchedEffect(Unit) {
        observeConnectivity(context).collect { state ->
            val wasOffline = viewModel.isOffline.value
            connectionState.value = state
            //If we go from offline to online, trigger a reload
            if (state is ConnectionState.Available && wasOffline) {
                viewModel.fetchNowPlayingMovies(BuildConfig.TMDB_API_KEY, context)
            }
        }
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

        Row {
            Button(onClick = {
                viewModel.fetchNowPlayingMovies(BuildConfig.TMDB_API_KEY, context)
            }) {
                Text("Now Playing")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                viewModel.fetchTopRatedMovies(BuildConfig.TMDB_API_KEY, context)
            }) {
                Text("Top Rated")
            }
        }


        LazyColumn {
            items(movieList) { movieSimple ->
                MovieListItemCard(
                    movieSimple = movieSimple,
                    onMovieListItemClicked = onMovieListItemClicked,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
    val isOffline by viewModel.isOffline.collectAsState()

    if (isOffline && !nowPlayingMovies.isEmpty()) {
        Text("You are offline.", color = Color.Red)
    }

    if (isOffline && nowPlayingMovies.isEmpty()) {
        Text("No data available for this list while offline.", color = Color.Red)
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
