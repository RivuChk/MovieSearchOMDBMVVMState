package dev.rivu.moviesearchomdb.moviesearch.presentation

import dev.rivu.moviesearchomdb.base.BasePresenter
import dev.rivu.moviesearchomdb.base.BaseView
import dev.rivu.moviesearchomdb.moviesearch.data.model.Movie

interface IMovieSearchPresenter: BasePresenter<IMovieSearchView> {
    fun searchMovies(searchText: String)
    fun getQueriesSuggestions(enteredText: String)
}

interface IMovieSearchView: BaseView {
    fun showMovies(moviesList: List<Movie>)
    fun showQueriesSuggestions(queries: List<String>)
    fun showError(errorDetails: String)
}