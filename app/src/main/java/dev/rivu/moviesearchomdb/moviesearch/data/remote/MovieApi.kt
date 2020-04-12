package dev.rivu.moviesearchomdb.moviesearch.data.remote

import dev.rivu.moviesearchomdb.BuildConfig
import dev.rivu.moviesearchomdb.moviesearch.data.remote.model.MovieSearchResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
    fun searchMovies(
        @Query("s") query: String,
        @Query("type") type: String = "movie",
        @Query("apikey") apikey: String = BuildConfig.ApiKey
    ): Single<MovieSearchResponse>
}