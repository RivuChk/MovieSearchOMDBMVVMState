package dev.rivu.moviesearchomdb.moviesearch.ui

import com.google.android.material.snackbar.Snackbar
import dev.rivu.moviesearchomdb.R
import dev.rivu.moviesearchomdb.base.BaseActivity
import dev.rivu.moviesearchomdb.moviesearch.data.model.Movie
import dev.rivu.moviesearchomdb.moviesearch.injection.inject
import dev.rivu.moviesearchomdb.moviesearch.presentation.IMovieSearchPresenter
import dev.rivu.moviesearchomdb.moviesearch.presentation.IMovieSearchView

import kotlinx.android.synthetic.main.activity_movie_search.*
import timber.log.Timber
import javax.inject.Inject

class MovieSearchActivity : BaseActivity<IMovieSearchView, IMovieSearchPresenter>(),
    IMovieSearchView {

    @Inject
    override lateinit var presenter: IMovieSearchPresenter

    override fun initView() {
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun layoutId(): Int = R.layout.activity_movie_search

    override fun injectDependencies() {
        inject()
    }

    override fun bind() {
        presenter.attachView(this)
        presenter.searchMovies("john")
    }

    override fun showMovies(moviesList: List<Movie>) {
        Timber.d("$moviesList")
    }

    override fun showQueriesSuggestions(queries: List<String>) {
        Timber.d("$queries")
    }

    override fun showError(errorDetails: String) {
        Timber.e("MainActivity :: $errorDetails")
    }
}
