package dev.rivu.moviesearchomdb.moviesearch.data.local

import dev.rivu.moviesearchomdb.moviesearch.data.MovieDataStore
import dev.rivu.moviesearchomdb.moviesearch.data.local.movies.MovieDao
import dev.rivu.moviesearchomdb.moviesearch.data.local.movies.MovieEntity
import dev.rivu.moviesearchomdb.moviesearch.data.local.query.QueryDao
import dev.rivu.moviesearchomdb.moviesearch.data.local.query.QueryEntity
import dev.rivu.moviesearchomdb.moviesearch.data.model.Movie
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class LocalMovieDataStore(
    private val movieDao: MovieDao,
    private val queryDao: QueryDao
) : MovieDataStore {
    override fun getMoviesStream(searchQuery: String): Observable<List<Movie>> {
        return RxJavaBridge.toV3Observable(
            queryDao.addQuery(QueryEntity(searchQuery))
                .onErrorComplete()
                .andThen(
                    movieDao.searchMoviesStream(searchQuery)
                )
        )
            .map { movieEntityList ->
                movieEntityList.map { movieEntity ->
                    Movie(
                        imdbID = movieEntity.imdbID,
                        poster = movieEntity.poster,
                        title = movieEntity.title,
                        type = movieEntity.type,
                        year = movieEntity.year
                    )
                }
            }
    }

    override fun getMovies(searchQuery: String): Single<List<Movie>> {
        return RxJavaBridge.toV3Single(
            queryDao.addQuery(QueryEntity(searchQuery))
                .onErrorComplete()
                .andThen(
                    movieDao.searchMovies(searchQuery)
                )
        )
            .map { movieEntityList ->
                movieEntityList.map { movieEntity ->
                    Movie(
                        imdbID = movieEntity.imdbID,
                        poster = movieEntity.poster,
                        title = movieEntity.title,
                        type = movieEntity.type,
                        year = movieEntity.year
                    )
                }
            }

    }

    override fun addMovies(movieList: List<Movie>): Completable {
        return RxJavaBridge.toV3Completable(
            movieDao.addMovies(movieList.map { movie ->
                MovieEntity(
                    imdbID = movie.imdbID,
                    poster = movie.poster,
                    title = movie.title,
                    type = movie.type,
                    year = movie.year
                )
            })
        )
    }

    override fun getSearchSuggestion(textEntered: String): Single<List<String>> {
        return RxJavaBridge.toV3Single(
            queryDao.searchQueries(textEntered)
                .map { queries ->
                    queries.map {
                        it.query
                    }
                }
        )
    }
}