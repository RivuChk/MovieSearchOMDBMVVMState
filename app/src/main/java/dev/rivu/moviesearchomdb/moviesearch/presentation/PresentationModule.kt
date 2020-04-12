package dev.rivu.moviesearchomdb.moviesearch.presentation

import dagger.Module
import dagger.Provides
import dev.rivu.moviesearchomdb.moviesearch.data.IMovieRepository
import dev.rivu.moviesearchomdb.injection.FeatureScope

@Module
class PresentationModule {
    @Provides
    @FeatureScope
    fun providesSchedulerProvider(): ISchedulerProvider = SchedulerProvider()
}
