package net.papanketik.moviescatalogue.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import net.papanketik.moviescatalogue.core.data.source.local.entity.MovieAndTvShowEntity

@Dao
interface MovieAndTvDao {
    @Query("SELECT * FROM tb_movies_and_tv where contentType = 0")
    fun getMovieData(): PagingSource<Int, MovieAndTvShowEntity>

    @Query("SELECT * FROM tb_movies_and_tv where contentType = 0 and bookmarked = 1")
    fun getBookmarkedMovieData(): PagingSource<Int, MovieAndTvShowEntity>

    @Query("SELECT * FROM tb_movies_and_tv where contentType = 0 and contentId = :movieId")
    fun getMovieDataById(movieId: Int): Flow<MovieAndTvShowEntity>

    @Query("SELECT * FROM tb_movies_and_tv where contentType = 1")
    fun getTvShowData(): PagingSource<Int, MovieAndTvShowEntity>

    @Query("SELECT * FROM tb_movies_and_tv where contentType = 1 and bookmarked = 1")
    fun getBookmarkedTvShowData(): PagingSource<Int, MovieAndTvShowEntity>

    @Query("SELECT * FROM tb_movies_and_tv where contentType = 1 and contentId = :tvShowId")
    fun getTvShowDataById(tvShowId: Int): Flow<MovieAndTvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContents(contents: List<MovieAndTvShowEntity>)

    @Update
    fun updateContent(content: MovieAndTvShowEntity)
}