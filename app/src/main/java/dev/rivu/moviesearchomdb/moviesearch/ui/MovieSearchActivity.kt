package dev.rivu.moviesearchomdb.moviesearch.ui

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.rivu.moviesearchomdb.base.BaseActivity
import dev.rivu.moviesearchomdb.databinding.ActivityMovieSearchBinding
import dev.rivu.moviesearchomdb.moviesearch.data.model.Movie
import dev.rivu.moviesearchomdb.moviesearch.injection.inject
import dev.rivu.moviesearchomdb.moviesearch.presentation.IMovieSearchPresenter
import dev.rivu.moviesearchomdb.moviesearch.presentation.IMovieSearchView
import dev.rivu.moviesearchomdb.utils.gone
import dev.rivu.moviesearchomdb.utils.visible
import timber.log.Timber
import javax.inject.Inject

class MovieSearchActivity :
    BaseActivity<IMovieSearchView, IMovieSearchPresenter, ActivityMovieSearchBinding>(),
    IMovieSearchView {

    @Inject
    override lateinit var presenter: IMovieSearchPresenter

    @Inject
    lateinit var adapter: MovieSearchAdapter

    override fun initView() {
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.rvMovies.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        binding.rvMovies.adapter = adapter
    }

    override fun bindView() = ActivityMovieSearchBinding.inflate(layoutInflater)

    override fun injectDependencies() {
        inject()
    }

    override fun bindPresentation() {
        presenter.attachView(this)
        presenter.searchMovies("john")
    }

    override fun showMovies(moviesList: List<Movie>) {
        binding.tvError.gone()
        binding.rvMovies.visible()
        Timber.d("$moviesList")
        adapter.submitList(moviesList)
    }

    override fun showQueriesSuggestions(queries: List<String>) {
        Timber.d("$queries")
    }

    override fun showError(errorDetails: String) {
        binding.tvError.text = errorDetails
        binding.tvError.visible()
    }
}
