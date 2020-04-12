package dev.rivu.moviesearchomdb.base

import io.reactivex.rxjava3.disposables.CompositeDisposable

interface BasePresenter<T: BaseView> {
    val disposables: CompositeDisposable
    var view: T?

    fun attachView(view: T) {
        this.view = view
    }

    fun detachView() {
        this.view = null
        disposables.clear()
    }
}

interface BaseView