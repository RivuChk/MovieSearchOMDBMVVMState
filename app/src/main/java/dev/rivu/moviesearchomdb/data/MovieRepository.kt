package dev.rivu.moviesearchomdb.data

import dev.rivu.moviesearchomdb.data.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction

class MovieRepository(
    private val localDataStore: MovieDataStore,
    private val remoteDataStore: MovieDataStore
) : IMovieRepository {
    override fun getMovies(searchQuery: String): Observable<List<Movie>> {
        return localDataStore.getMoviesStream(searchQuery)
    }

    override fun fetchAndStoreMovies(searchQuery: String): Completable {
        return remoteDataStore.getMovies(searchQuery)
            .flatMapCompletable { movies ->
                addMovies(movies)
            }
    }

    override fun addMovies(movieList: List<Movie>): Completable {
        return localDataStore.addMovies(movieList)
    }

    override fun getSearchSuggestion(textEntered: String): Single<List<String>> {
        return localDataStore.getSearchSuggestion(textEntered)
    }
}