package net.papanketik.moviescatalogue.core.utils

import androidx.paging.PagingData
import androidx.paging.map
import net.papanketik.moviescatalogue.core.data.MovieAndTvShowRepository
import net.papanketik.moviescatalogue.core.data.source.local.entity.MovieAndTvShowEntity
import net.papanketik.moviescatalogue.core.data.source.remote.response.DetailMovieResponse
import net.papanketik.moviescatalogue.core.data.source.remote.response.DetailTvResponse
import net.papanketik.moviescatalogue.core.data.source.remote.response.ResultsMovieItem
import net.papanketik.moviescatalogue.core.data.source.remote.response.ResultsTvItem
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow

object DataMapper {
    fun mapMovieResponsesToEntities(responses: List<ResultsMovieItem>): List<MovieAndTvShowEntity> {
        val dataList = ArrayList<MovieAndTvShowEntity>()
        responses.map { response ->
            val movie =
                MovieAndTvShowEntity(
                    response.id,
                    "https://image.tmdb.org/t/p/w500${response.posterPath}",
                    response.title,
                    response.voteAverage,
                    response.overview,
                    response.releaseDate?.split("-")?.get(0),
                    MovieAndTvShowRepository.MOVIE_CONTENT_TYPE
                )
            dataList.add(movie)
        }
        return dataList
    }

    fun mapDetailMovieResponseToEntity(response: DetailMovieResponse) =
        MovieAndTvShowEntity(
            response.id,
            "https://image.tmdb.org/t/p/w500${response.posterPath}",
            response.title,
            response.voteAverage,
            response.overview,
            response.releaseDate.split("-")[0],
            MovieAndTvShowRepository.MOVIE_CONTENT_TYPE
        )

    fun mapTvShowResponsesToEntities(responses: List<ResultsTvItem>): List<MovieAndTvShowEntity> {
        val dataList = ArrayList<MovieAndTvShowEntity>()
        responses.map { response ->
            val tvShow =
                MovieAndTvShowEntity(
                    response.id,
                    "https://image.tmdb.org/t/p/w500${response.posterPath}",
                    response.name,
                    response.voteAverage,
                    response.overview,
                    response.firstAirDate.split("-")[0],
                    MovieAndTvShowRepository.TV_SHOW_CONTENT_TYPE
                )
            dataList.add(tvShow)
        }
        return dataList
    }

    fun mapDetailTvShowResponseToEntity(response: DetailTvResponse) =
        MovieAndTvShowEntity(
            response.id,
            "https://image.tmdb.org/t/p/w500${response.posterPath}",
            response.name,
            response.voteAverage,
            response.overview,
            response.firstAirDate.split("-")[0],
            MovieAndTvShowRepository.TV_SHOW_CONTENT_TYPE
        )

    fun mapEntitiesToDomain(entities: PagingData<MovieAndTvShowEntity>): PagingData<MovieAndTvShow> {
        return entities.map { entity ->
            MovieAndTvShow(
                id = entity.id,
                poster = entity.poster,
                title = entity.title,
                rating = entity.rating,
                overview = entity.overview,
                year = entity.year,
                contentType = entity.contentType,
                bookmarked = entity.bookmarked
            )
        }
    }

    fun mapDetailEntityToDomain(entity: MovieAndTvShowEntity) = MovieAndTvShow(
        id = entity.id,
        poster = entity.poster,
        title = entity.title,
        rating = entity.rating,
        overview = entity.overview,
        year = entity.year,
        contentType = entity.contentType,
        bookmarked = entity.bookmarked
    )

    fun mapDomainToEntity(domain: MovieAndTvShow) =
        MovieAndTvShowEntity(
            id = domain.id,
            poster = domain.poster,
            title = domain.title,
            rating = domain.rating,
            overview = domain.overview,
            year = domain.year,
            contentType = domain.contentType,
            bookmarked = domain.bookmarked
        )

    fun mapEntityToDomain(entity: MovieAndTvShowEntity) =
        MovieAndTvShow(
            id = entity.id,
            poster = entity.poster,
            title = entity.title,
            rating = entity.rating,
            overview = entity.overview,
            year = entity.year,
            contentType = entity.contentType,
            bookmarked = entity.bookmarked
        )
}