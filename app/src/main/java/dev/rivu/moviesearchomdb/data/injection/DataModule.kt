package dev.rivu.moviesearchomdb.data.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.rivu.moviesearchomdb.data.IMovieRepository
import dev.rivu.moviesearchomdb.data.MovieDataStore
import dev.rivu.moviesearchomdb.data.MovieRepository
import dev.rivu.moviesearchomdb.data.local.LocalMovieDataStore
import dev.rivu.moviesearchomdb.data.local.MovieSearchDB
import dev.rivu.moviesearchomdb.data.local.movies.MovieDao
import dev.rivu.moviesearchomdb.data.local.query.QueryDao
import dev.rivu.moviesearchomdb.data.remote.MovieApi
import dev.rivu.moviesearchomdb.data.remote.MovieApiFactory
import dev.rivu.moviesearchomdb.data.remote.RemoteMovieDataStore
import dev.rivu.moviesearchomdb.injection.FeatureScope
import javax.inject.Named

@Module
class DataModule(val applicationContext: Context) {

    @Provides
    @FeatureScope
    fun provideMovieSearchApi(): MovieApi = MovieApiFactory.makeMovieApi()

    @Provides
    @FeatureScope
    fun provideDatabase(): MovieSearchDB = MovieSearchDB.getInstance(applicationContext)

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