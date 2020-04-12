package dev.rivu.moviesearchomdb.data.local.movies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Query("select * from tbl_movie")
    fun getAllMovies(): Single<List<MovieEntity>>

    @Query("select * from tbl_movie")
    fun getAllMoviesStream(): Observable<List<MovieEntity>>

    @Query("select * from tbl_movie where title like '%' || :query || '%' and type = :type")
    fun searchMoviesStream(query: String, type: String = "movie"): Observable<List<MovieEntity>>

    @Query("select * from tbl_movie where title like '%' || :query || '%' and type = :type")
    fun searchMovies(query: String, type: String = "movie"): Single<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovie(movieEntity: MovieEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(list: List<MovieEntity>): Completable
}