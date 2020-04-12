package dev.rivu.moviesearchomdb.moviesearch.data.local.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_movie")
data class MovieEntity (
    @PrimaryKey
    val imdbID: String = "", // tt7728344
    val poster: String = "", // https://m.media-amazon.com/images/M/MV5BZGU5YTVlZTktNzgzMS00MGVlLTgyMGMtNWYwNTkwNGY1MzllXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg
    val title: String = "", // Marvel Rising: Secret Warriors
    val type: String = "", // movie
    val year: String = "" // 2018
)