package dev.rivu.moviesearchomdb.moviesearch.injection

import dev.rivu.moviesearchomdb.coreComponent
import dev.rivu.moviesearchomdb.moviesearch.data.injection.DataModule
import dev.rivu.moviesearchomdb.moviesearch.presentation.PresentationModule
import dev.rivu.moviesearchomdb.moviesearch.ui.MovieSearchActivity

fun MovieSearchActivity.inject() {
    DaggerMovieSearchComponent.builder()
        .coreComponent(coreComponent())
        .dataModule(DataModule())
        .presentationModule(PresentationModule())
        .movieSearchModule(MovieSearchModule())
        .movieSearchActivity(this)
        .build()
        .inject(this)
}