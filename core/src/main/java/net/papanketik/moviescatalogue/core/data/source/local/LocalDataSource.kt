package net.papanketik.moviescatalogue.core.data.source.local

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import net.papanketik.moviescatalogue.core.data.source.local.entity.MovieAndTvShowEntity
import net.papanketik.moviescatalogue.core.data.source.local.room.MovieAndTvDao

class LocalDataSource (private val movieAndTvDao: MovieAndTvDao) {
    fun getMovieData(): PagingSource<Int, MovieAndTvShowEntity> = movieAndTvDao.getMovieData()

    fun getBookmarkedMovieData(): PagingSource<Int, MovieAndTvShowEntity> = movieAndTvDao.getBookmarkedMovieData()

    fun getMovieDataById(movieId: Int): Flow<MovieAndTvShowEntity> = movieAndTvDao.getMovieDataById(movieId)

    fun getTvShowData(): PagingSource<Int, MovieAndTvShowEntity> = movieAndTvDao.getTvShowData()

    fun getBookmarkedTvShowData(): PagingSource<Int, MovieAndTvShowEntity> = movieAndTvDao.getBookmarkedTvShowData()

    fun getTvShowDataById(tvShowId: Int): Flow<MovieAndTvShowEntity> = movieAndTvDao.getTvShowDataById(tvShowId)

    suspend fun insertContents(contents: List<MovieAndTvShowEntity>) = movieAndTvDao.insertContents(contents)

    fun setBookmarkContent(content: MovieAndTvShowEntity, state: Boolean) {
        content.bookmarked = state
        movieAndTvDao.updateContent(content)
    }

    fun updateContent(content: MovieAndTvShowEntity) = movieAndTvDao.updateContent(content)
}