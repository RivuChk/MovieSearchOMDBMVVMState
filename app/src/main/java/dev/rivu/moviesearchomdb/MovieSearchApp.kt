package dev.rivu.moviesearchomdb

import android.app.Application
import timber.log.Timber

open class MovieSearchApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            //Plant crash/log reporting tool with Timber
        }
    }
}