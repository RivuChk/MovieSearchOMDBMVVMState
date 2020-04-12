package dev.rivu.moviesearchomdb.moviesearch.injection

import dagger.BindsInstance
import dagger.Component
import dev.rivu.moviesearchomdb.injection.CoreComponent
import dev.rivu.moviesearchomdb.injection.FeatureScope
import dev.rivu.moviesearchomdb.moviesearch.data.injection.DataModule
import dev.rivu.moviesearchomdb.moviesearch.presentation.PresentationModule
import dev.rivu.moviesearchomdb.moviesearch.ui.MovieSearchActivity

@FeatureScope
@Component(modules = [MovieSearchModule::class], dependencies = [CoreComponent::class])
interface MovieSearchComponent {
    fun inject(activity: MovieSearchActivity)

    @Component.Builder
    interface Builder {
        fun build(): MovieSearchComponent
        @BindsInstance
        fun movieSearchActivity(activity: MovieSearchActivity): Builder

        fun coreComponent(module: CoreComponent): Builder
        fun movieSearchModule(module: MovieSearchModule): Builder
        fun dataModule(module: DataModule): Builder
        fun presentationModule(module: PresentationModule): Builder
    }
}