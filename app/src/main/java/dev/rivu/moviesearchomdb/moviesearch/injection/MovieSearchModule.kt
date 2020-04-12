package dev.rivu.moviesearchomdb.moviesearch.injection

import dagger.Module
import dev.rivu.moviesearchomdb.moviesearch.data.injection.DataModule
import dev.rivu.moviesearchomdb.moviesearch.presentation.PresentationModule

@Module(includes = [DataModule::class, PresentationModule::class])
class MovieSearchModule