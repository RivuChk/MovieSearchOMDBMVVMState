package dev.rivu.moviesearchomdb.moviesearch.data.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.rivu.moviesearchomdb.moviesearch.data.IMovieRepository
import dev.rivu.moviesearchomdb.moviesearch.data.MovieDataStore
import dev.rivu.moviesearchomdb.moviesearch.data.MovieRepository
import dev.rivu.moviesearchomdb.moviesearch.data.local.LocalMovieDataStore
import dev.rivu.moviesearchomdb.moviesearch.data.local.MovieSearchDB
import dev.rivu.moviesearchomdb.moviesearch.data.local.movies.MovieDao
import dev.rivu.moviesearchomdb.moviesearch.data.local.query.QueryDao
import dev.rivu.moviesearchomdb.moviesearch.data.remote.MovieApi
import dev.rivu.moviesearchomdb.moviesearch.data.remote.MovieApiFactory
import dev.rivu.moviesearchomdb.moviesearch.data.remote.RemoteMovieDataStore
import dev.rivu.moviesearchomdb.injection.FeatureScope
import javax.inject.Named

@Module
class DataModule() {

    @Provides
    @FeatureScope
    fun provideMovieSearchApi(): MovieApi = MovieApiFactory.makeMovieApi()

    @Provides
    @FeatureScope
    fun provideDatabase(applicationContext: Context): MovieSearchDB = MovieSearchDB.getInstance(applicationContext)

    @Provides
    @FeatureScope
    fun provideMovieDao(db: MovieSearchDB): MovieDao = db.movieDao()

    @Provides
    @FeatureScope
    fun provideQueryDao(db: MovieSearchDB): QueryDao = db.queryDao()

    @Provides
    @FeatureScope
    @Named("local")
    fun provideLocalDataStore(movieDao: MovieDao, queryDao: QueryDao): MovieDataStore =
        LocalMovieDataStore(movieDao, queryDao)

    @Provides
    @FeatureScope
    @Named("remote")
    fun provideRemoteDataStore(movieApi: MovieApi): MovieDataStore =
        RemoteMovieDataStore(movieApi)

    @Provides
    @FeatureScope
    fun provideRepository(
        @Named("local") localDataStore: MovieDataStore,
        @Named("remote") remoteDataStore: MovieDataStore
    ): IMovieRepository = MovieRepository(localDataStore, remoteDataStore)
}