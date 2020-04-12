package dev.rivu.moviesearchomdb.moviesearch.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.rivu.moviesearchomdb.injection.FeatureScope
import dev.rivu.moviesearchomdb.moviesearch.data.IMovieRepository
import dev.rivu.moviesearchomdb.moviesearch.data.model.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@FeatureScope
class MovieSearchViewModel @Inject constructor(
    private val repository: IMovieRepository,
    private val schedulerProvider: ISchedulerProvider
) : ViewModel() {

    val disposables: CompositeDisposable = CompositeDisposable()

    val searchSuggestions: LiveData<List<String>> = MutableLiveData()
    val searchResults: LiveData<List<Movie>> = MutableLiveData()
    val error: LiveData<String> = MutableLiveData()

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
                                (searchResults as MutableLiveData).postValue(moviesList)
                            } else {
                                (error as MutableLiveData).postValue("Couldn't find a movie that matches the search")
                            }
                        },
                        {
                            (error as MutableLiveData).postValue("There was an error fetching movies")
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
                        (searchSuggestions as MutableLiveData).postValue(suggestions)
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