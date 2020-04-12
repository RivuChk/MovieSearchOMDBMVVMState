package dev.rivu.moviesearchomdb.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.rivu.moviesearchomdb.MovieSearchApp
import javax.inject.Singleton

@Module
class CoreModule(val app: MovieSearchApp) {

    @Provides
    @Singleton
    fun appContext(): Context = app
}