package dev.rivu.moviesearchomdb.data.remote

import dev.rivu.moviesearchomdb.BuildConfig
import dev.rivu.moviesearchomdb.data.remote.model.MovieSearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query

interface MovieApi {

    fun searchMovies(
        @Query("s") query: String,
        @Query("type") type: String = "movie",
        @Query("apikey") apikey: String = BuildConfig.ApiKey
    ): Single<MovieSearchResponse>
}