package dev.rivu.moviesearchomdb.moviesearch.data.remote

import dev.rivu.moviesearchomdb.moviesearch.data.MovieDataStore
import dev.rivu.moviesearchomdb.moviesearch.data.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class RemoteMovieDataStore(private val movieApi: MovieApi) : MovieDataStore {
    override fun getMoviesStream(searchQuery: String): Observable<List<Movie>> {
        return Observable.error(UnsupportedOperationException("Can't get stream from Remote"))
    }

    override fun getMovies(searchQuery: String): Single<List<Movie>> {
        return movieApi.searchMovies(searchQuery)
            .map { movieResponse ->
                movieResponse.movies
                    .map { movie ->
                        Movie(
                            imdbID = movie.imdbID,
                            poster = movie.poster,
                            title = movie.title,
                            type = movie.type,
                            year = movie.year
                        )
                    }
            }
    }

    override fun addMovies(movieList: List<Movie>): Completable {
        return Completable.error(UnsupportedOperationException("Can't add to Remote"))
    }

    override fun getSearchSuggestion(textEntered: String): Single<List<String>> {
        return Single.error(UnsupportedOperationException("Remote doesn't support search suggestion"))
    }
}