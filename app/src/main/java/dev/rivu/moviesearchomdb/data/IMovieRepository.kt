package dev.rivu.moviesearchomdb.data

import dev.rivu.moviesearchomdb.data.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface IMovieRepository {
    fun getMovies(searchQuery: String): Observable<List<Movie>>

    fun addMovies(movieList: List<Movie>): Completable

    fun getSearchSuggestion(textEntered: String): Single<List<String>>

    fun fetchAndStoreMovies(searchQuery: String): Completable
}