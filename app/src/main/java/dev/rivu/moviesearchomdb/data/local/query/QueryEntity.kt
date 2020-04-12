package dev.rivu.moviesearchomdb.data.local.query

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_query")
data class QueryEntity(
    @PrimaryKey
    val query: String
)