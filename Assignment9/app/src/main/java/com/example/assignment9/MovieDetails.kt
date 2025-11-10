@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.assignment9

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun MovieDetailScreen(
    navController: NavController,
    imdbId: String,
    movieViewModel: MovieViewModel = viewModel()
) {
    var movieDetail by remember { mutableStateOf<com.example.assignment9.api.OmdbResponse?>(null) }
    val isLoading by movieViewModel.isLoading.collectAsState()
    val error by movieViewModel.error.collectAsState()

    LaunchedEffect(imdbId) {
        movieDetail = movieViewModel.getMovieDetailsById(imdbId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movieDetail?.title ?: "Movie Details") },
                navigationIcon = {
                    TextButton(onClick = { navController.popBackStack() }) { Text("Back") }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text(
                    text = error ?: "Error loading movie details.",
                    color = MaterialTheme.colorScheme.error
                )
                movieDetail != null -> MovieDetailContent(movieDetail!!)
            }
        }
    }
}

@Composable
fun MovieDetailContent(movie: com.example.assignment9.api.OmdbResponse) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!movie.poster.isNullOrEmpty() && movie.poster != "N/A") {
                    AsyncImage(
                        model = movie.poster,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(220.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.height(16.dp))
                }

                Text(
                    text = movie.title ?: "No Title",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(8.dp))

                Text("Year: ${movie.year ?: "N/A"}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                Text("Rated: ${movie.rated ?: "N/A"}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                Text("Director: ${movie.director ?: "N/A"}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                Text("Actors: ${movie.actors ?: "N/A"}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                Text("IMDb Rating: ${movie.imdbRating ?: "N/A"}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                Text("Box Office: ${movie.boxOffice ?: "N/A"}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Plot:",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = movie.plot ?: "No plot available.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
