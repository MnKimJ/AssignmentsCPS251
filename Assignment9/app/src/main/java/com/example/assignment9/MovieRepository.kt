package com.example.assignment9

import com.example.assignment9.api.OmdbApiService
import com.example.assignment9.api.OmdbResponse

class MovieRepository(
    private val api: OmdbApiService,
    private val apiKey: String
) {
    suspend fun searchMovies(query: String): Result<OmdbResponse> {
        return try {
            val response = api.search(apiKey, query)
            if (response.response.equals("True", true)) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.error ?: "No movies found."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMovieDetails(imdbId: String): Result<OmdbResponse> {
        return try {
            val response = api.getDetails(apiKey, imdbId)
            if (response.response.equals("True", true)) {
                Result.success(response)
            } else {
                Result.failure(Exception(response.error ?: "Failed to fetch details."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
