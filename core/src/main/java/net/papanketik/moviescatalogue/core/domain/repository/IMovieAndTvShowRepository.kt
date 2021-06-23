package net.papanketik.moviescatalogue.core.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow
import net.papanketik.moviescatalogue.core.vo.Resource

interface IMovieAndTvShowRepository {
    fun getMovieData(): Flow<Resource<PagingData<MovieAndTvShow>>>
    fun getMovieDataById(movieId: Int): Flow<Resource<MovieAndTvShow>>
    fun getBookmarkedMovieData(): Flow<PagingData<MovieAndTvShow>>
    fun getTvShowData(): Flow<Resource<PagingData<MovieAndTvShow>>>
    fun getTvShowDataById(tvShowId: Int): Flow<Resource<MovieAndTvShow>>
    fun getBookmarkedTvShowData(): Flow<PagingData<MovieAndTvShow>>
    fun setContentBookmark(content: MovieAndTvShow, state: Boolean)
    fun getMovieDataTest(): ArrayList<MovieAndTvShow>
}