package dev.rivu.moviesearchomdb

import android.app.Activity
import android.app.Application
import android.content.Context
import dev.rivu.moviesearchomdb.injection.CoreComponent
import dev.rivu.moviesearchomdb.injection.CoreModule
import dev.rivu.moviesearchomdb.injection.DaggerCoreComponent
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

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder()
            .coreModule(CoreModule(this))
            .build()
    }

    companion object {
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as MovieSearchApp).coreComponent
    }
}

fun Activity.coreComponent() = MovieSearchApp.coreComponent(this)