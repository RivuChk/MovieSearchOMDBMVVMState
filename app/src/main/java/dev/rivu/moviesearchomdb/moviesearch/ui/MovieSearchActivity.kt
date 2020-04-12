package dev.rivu.moviesearchomdb.moviesearch.ui

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.rivu.moviesearchomdb.base.BaseActivity
import dev.rivu.moviesearchomdb.databinding.ActivityMovieSearchBinding
import dev.rivu.moviesearchomdb.moviesearch.data.model.Movie
import dev.rivu.moviesearchomdb.moviesearch.injection.inject
import dev.rivu.moviesearchomdb.moviesearch.presentation.MovieSearchState
import dev.rivu.moviesearchomdb.moviesearch.presentation.MovieSearchViewModel
import dev.rivu.moviesearchomdb.utils.gone
import dev.rivu.moviesearchomdb.utils.visible
import timber.log.Timber
import javax.inject.Inject

class MovieSearchActivity :
    BaseActivity<ActivityMovieSearchBinding, MovieSearchViewModel, MovieSearchState>() {

    @Inject
    override lateinit var viewModel: MovieSearchViewModel

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
        viewModel.searchState
            .observe(this, Observer {
                render(it)
            })
        viewModel.searchMovies("jack")
    }

    override fun render(state: MovieSearchState) {
        when {
            state.isLoading -> {
                //show loading
            }
            state.error.isNotEmpty() -> {
                binding.tvError.text = state.error
                binding.tvError.visible()
            }
            state.movies.isNotEmpty() -> {
                binding.tvError.gone()
                binding.rvMovies.visible()
                Timber.d("${state.movies}")
                adapter.submitList(state.movies)
            }
        }
    }
}
