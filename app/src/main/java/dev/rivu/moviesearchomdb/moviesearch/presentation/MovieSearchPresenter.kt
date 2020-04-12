package dev.rivu.moviesearchomdb.moviesearch.presentation

import dev.rivu.moviesearchomdb.moviesearch.data.IMovieRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable


class MovieSearchPresenter(
    private val repository: IMovieRepository,
    private val schedulerProvider: ISchedulerProvider
) : IMovieSearchPresenter {
    override var view: IMovieSearchView? = null

    override val disposables: CompositeDisposable = CompositeDisposable()

    override fun searchMovies(searchText: String) {
        if (searchText.isNotEmpty()) {
            //Clear previous requests / subscriptions
            disposables.clear()

            //Search from db first, then sync from API
            disposables.addAll(
                repository.getMovies(searchText)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                        { moviesList ->
                            if (moviesList.isNotEmpty()) {
                                view?.showMovies(moviesList)
                            } else {
                                view?.showError("Couldn't find a movie that matches the search")
                            }
                        },
                        {
                            view?.showError("There was an error fetching movies")
                        }
                    ),
                repository.syncMovieSearchResult(searchText)
                    .onErrorComplete()
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe()
            )

        }
    }

    override fun getQueriesSuggestions(enteredText: String) {
        disposables.add(
            repository.getSearchSuggestion(enteredText)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { suggestions ->
                        view?.showQueriesSuggestions(suggestions)
                    },
                    {
                        //ignore this error
                    }
                )
        )
    }
}