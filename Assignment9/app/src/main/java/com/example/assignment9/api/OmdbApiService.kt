package com.example.assignment9.api

import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun search(
        @Query("apikey") apiKey: String,
        @Query("s") query: String,
        @Query("page") page: Int? = null
    ): com.example.assignment9.api.OmdbResponse

    @GET("/")
    suspend fun getDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String,
        @Query("plot") plot: String = "short"
    ): com.example.assignment9.api.OmdbResponse
}