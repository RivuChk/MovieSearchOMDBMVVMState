package dev.rivu.moviesearchomdb.injection

import android.content.Context
import dagger.Component
import dev.rivu.moviesearchomdb.MovieSearchApp
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent {
    fun appContext(): Context

    @Component.Builder
    interface Builder {
        fun coreModule(coreModule: CoreModule): Builder
        fun build(): CoreComponent
    }
}