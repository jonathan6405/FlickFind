package com.example.moviedb2025.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviedb2025.models.Movie
import com.example.moviedb2025.utils.Constants

@Composable
fun MovieGridScreen(
    movieList: List<Movie>,
    onMovieClicked: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), //This changes amount of columns, set to 2 for now
        modifier = modifier.padding(8.dp)
    ) {
        items(movieList) { movie ->
            Card(
                modifier = Modifier.padding(8.dp),
                onClick = { onMovieClicked(movie) }
            ) {
                AsyncImage(
                    model = Constants.POSTER_IMAGE_BASE_URL + Constants.POSTER_IMAGE_BASE_WIDTH + movie.posterPath,
                    contentDescription = movie.title,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = movie.title,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
