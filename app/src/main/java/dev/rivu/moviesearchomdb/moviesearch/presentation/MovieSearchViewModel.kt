package dev.rivu.moviesearchomdb.moviesearch.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.rivu.moviesearchomdb.injection.FeatureScope
import dev.rivu.moviesearchomdb.moviesearch.data.IMovieRepository
import dev.rivu.moviesearchomdb.utils.emptyString
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@FeatureScope
class MovieSearchViewModel @Inject constructor(
    private val repository: IMovieRepository,
    private val schedulerProvider: ISchedulerProvider
) : ViewModel() {

    val disposables: CompositeDisposable = CompositeDisposable()

    val searchState: LiveData<MovieSearchState> = MutableLiveData(MovieSearchState())

    fun searchMovies(searchText: String) {
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
                                (searchState as MutableLiveData).postValue(
                                    searchState.value!!.copy(
                                        movies = moviesList,
                                        error = emptyString(),
                                        isLoading = false
                                    )
                                )
                            } else {
                                (searchState as MutableLiveData).postValue(
                                    searchState.value!!.copy(
                                        error = "Couldn't find a movie that matches the search",
                                        isLoading = false
                                    )
                                )
                            }
                        },
                        {
                            (searchState as MutableLiveData).postValue(
                                searchState.value!!.copy(
                                    error = "Couldn't find a movie that matches the search",
                                    isLoading = false
                                )
                            )
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

    fun getQueriesSuggestions(enteredText: String) {
        disposables.add(
            repository.getSearchSuggestion(enteredText)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    { suggestions ->
                        (searchState as MutableLiveData).postValue(
                            searchState.value!!.copy(
                                suggestions = suggestions
                            )
                        )
                    },
                    {
                        //ignore this error
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}