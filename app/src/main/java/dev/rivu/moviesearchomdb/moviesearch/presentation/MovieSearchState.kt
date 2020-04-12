package dev.rivu.moviesearchomdb.moviesearch.presentation

import dev.rivu.moviesearchomdb.moviesearch.data.model.Movie

data class MovieSearchState(
    val movies: List<Movie> = listOf(),
    val suggestions: List<String> = listOf(),
    val error: String = "",
    val isLoading: Boolean = false
)