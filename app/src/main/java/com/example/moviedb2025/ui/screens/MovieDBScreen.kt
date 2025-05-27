@file:kotlin.OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("INFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION_WARNING")

package com.example.moviedb2025.ui.screens

import androidx.annotation.OptIn
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviedb2025.R
import com.example.moviedb2025.models.Genre
import com.example.moviedb2025.models.Movie
import com.example.moviedb2025.models.MovieSimple
import com.example.moviedb2025.ui.theme.MovieDB2025Theme
import com.example.moviedb2025.viewmodel.MovieDBViewModel
import androidx.compose.runtime.LaunchedEffect
import com.example.moviedb2025.BuildConfig
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moviedb2025.viewmodel.MovieDBViewModelFactory


enum class MovieDBScreen(@StringRes val title: Int){
    List(title = R.string.app_name),
    Detail(title = R.string.movie_detail),
    Third(title = R.string.third_screen),
    Grid(title = R.string.grid_screen)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDBAppBar(
    currScreen: MovieDBScreen,
    canNavigateBack:Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {Text(stringResource(currScreen.title))},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}


@Composable
fun MovieDbApp(
    viewModel: MovieDBViewModel = viewModel(
        factory = MovieDBViewModelFactory(LocalContext.current.applicationContext)
    ),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntity by navController.currentBackStackEntryAsState()

    val currentScreen = MovieDBScreen.valueOf(
        backStackEntity?.destination?.route ?: MovieDBScreen.List.name
    )

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchNowPlayingMovies(BuildConfig.TMDB_API_KEY, context)
    }


    Scaffold(
        topBar = {
            MovieDBAppBar(currScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = MovieDBScreen.List.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            composable(route = MovieDBScreen.List.name){
                MovieListScreen(
                    onMovieListItemClicked = { movieSimple ->
                        viewModel.setSelectedMovieSimple(movieSimple)
                        navController.navigate(MovieDBScreen.Detail.name)
                    },
                    navController = navController,
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                )

            }
            composable(route = MovieDBScreen.Detail.name){
                Log.d("MovieDBScreen", "Navigating to detail screen with movieSimple: ${uiState.selectedMovieSimple?.title}")
                uiState.selectedMovieSimple?.let { movie ->
                    MovieDetailScreen(movie = movie,
                        modifier = Modifier)
                }
            }
            composable(route = MovieDBScreen.Third.name) {
                ThirdScreen()
            }
            composable(route = MovieDBScreen.Grid.name) {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                MovieGridScreen(
                    movieList = uiState.movieList,
                    onMovieClicked = { movieSimple ->
                        viewModel.setSelectedMovieSimple(movieSimple)
                        navController.navigate(MovieDBScreen.Detail.name)
                    }
                )
            }

        }


    }
}
