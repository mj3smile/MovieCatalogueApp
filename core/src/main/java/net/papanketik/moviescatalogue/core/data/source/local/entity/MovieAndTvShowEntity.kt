package net.papanketik.moviescatalogue.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_movies_and_tv")
data class MovieAndTvShowEntity(
    @PrimaryKey
    @ColumnInfo(name = "contentId")
    var id: Int?,

    @ColumnInfo(name = "posterPath")
    var poster: String?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "rating")
    var rating: Double?,

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "year")
    var year: String?,

    @ColumnInfo(name = "contentType")
    var contentType: Int?,

    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false
)