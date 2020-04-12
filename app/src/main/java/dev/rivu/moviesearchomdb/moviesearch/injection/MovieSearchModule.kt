package dev.rivu.moviesearchomdb.moviesearch.injection

import dagger.Module
import dagger.Provides
import dev.rivu.moviesearchomdb.injection.FeatureScope
import dev.rivu.moviesearchomdb.moviesearch.data.injection.DataModule
import dev.rivu.moviesearchomdb.moviesearch.presentation.PresentationModule
import dev.rivu.moviesearchomdb.moviesearch.ui.MovieSearchAdapter

@Module(includes = [DataModule::class, PresentationModule::class])
class MovieSearchModule {

    @Provides
    @FeatureScope
    fun provideMovieAdapter(): MovieSearchAdapter = MovieSearchAdapter()
}