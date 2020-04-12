package dev.rivu.moviesearchomdb.moviesearch.data

import dev.rivu.moviesearchomdb.moviesearch.data.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface IMovieRepository {
    fun getMovies(searchQuery: String): Flowable<List<Movie>>

    fun addMovies(movieList: List<Movie>): Completable

    fun getSearchSuggestion(textEntered: String): Single<List<String>>

    fun syncMovieSearchResult(searchQuery: String): Single<List<Movie>>
}