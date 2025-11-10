package com.example.assignment9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val repository = MovieRepository(
                        api = com.example.assignment9.api.RetrofitInstance.api,
                        apiKey = "9bf5e3e6"
                    )
                    val movieViewModel: MovieViewModel =
                        viewModel(factory = MovieViewModel.provideFactory(repository))
                    NavHost(
                        navController = navController,
                        startDestination = "movie_search"
                    ) {
                        composable("movie_search") {
                            MovieSearchScreen(
                                navController = navController,
                                movieViewModel = movieViewModel
                            )
                        }
                        composable(
                            route = "movie_details/{imdbId}",
                            arguments = listOf(navArgument("imdbId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val imdbId = backStackEntry.arguments?.getString("imdbId") ?: ""
                            MovieDetailScreen(
                                navController = navController,
                                imdbId = imdbId,
                                movieViewModel = movieViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
