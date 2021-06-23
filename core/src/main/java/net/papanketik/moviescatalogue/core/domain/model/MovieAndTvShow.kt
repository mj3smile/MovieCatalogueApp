package net.papanketik.moviescatalogue.core.domain.model

data class MovieAndTvShow(
    val id: Int?,
    val poster: String?,
    val title: String?,
    val rating: Double?,
    val overview: String?,
    val year: String?,
    val contentType: Int?,
    val bookmarked: Boolean = false
)
