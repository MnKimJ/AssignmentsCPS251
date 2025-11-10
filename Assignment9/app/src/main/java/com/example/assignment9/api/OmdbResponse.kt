package com.example.assignment9.api

import com.squareup.moshi.Json

data class OmdbResponse(
    @Json(name = "Search") val search: List<MovieSearchItem>?,
    @Json(name = "totalResults") val totalResults: String?,
    @Json(name = "Response") val response: String,
    @Json(name = "Error") val error: String?,
    @Json(name = "Title") val title: String? = null,
    @Json(name = "Year") val year: String? = null,
    @Json(name = "Rated") val rated: String? = null,
    @Json(name = "Released") val released: String? = null,
    @Json(name = "Runtime") val runtime: String? = null,
    @Json(name = "Genre") val genre: String? = null,
    @Json(name = "Director") val director: String? = null,
    @Json(name = "Actors") val actors: String? = null,
    @Json(name = "Plot") val plot: String? = null,
    @Json(name = "Poster") val poster: String? = null,
    @Json(name = "imdbID") val imdbID: String? = null,
    @Json(name = "imdbRating") val imdbRating: String? = null,
    @Json(name = "imdbVotes") val imdbVotes: String? = null,
    @Json(name = "BoxOffice") val boxOffice: String? = null
)

data class MovieSearchItem(
    @Json(name = "Title") val title: String?,
    @Json(name = "Year") val year: String?,
    @Json(name = "imdbID") val imdbID: String?,
    @Json(name = "Type") val type: String?,
    @Json(name = "Poster") val poster: String?
)
