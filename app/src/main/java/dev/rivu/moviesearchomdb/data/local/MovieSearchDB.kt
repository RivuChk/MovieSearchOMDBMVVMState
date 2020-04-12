package dev.rivu.moviesearchomdb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.rivu.moviesearchomdb.data.local.movies.MovieDao
import dev.rivu.moviesearchomdb.data.local.movies.MovieEntity
import dev.rivu.moviesearchomdb.data.local.query.QueryDao
import dev.rivu.moviesearchomdb.data.local.query.QueryEntity

@Database(entities = [MovieEntity::class, QueryEntity::class], version = 1)
abstract class MovieSearchDB: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun queryDao(): QueryDao

    companion object {
        private var instance: MovieSearchDB? = null

        @JvmStatic @Synchronized
        fun getInstance(applicationContext: Context): MovieSearchDB {
            if (instance == null) {
                instance = Room.databaseBuilder(applicationContext, MovieSearchDB::class.java, "movie_search.db").build()
            }

            return instance!!
        }
    }
}