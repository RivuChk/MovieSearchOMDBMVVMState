package dev.rivu.moviesearchomdb.data.local.query

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface QueryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuery(queryEntity: QueryEntity): Completable

    @Query("select * from tbl_query where `query` like '%' || :text || '%'")
    fun searchQueries(text: String): Single<List<QueryEntity>>

    @Query("select * from tbl_query")
    fun getAllQueries(): Single<List<QueryEntity>>
}