package net.papanketik.moviescatalogue.core.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow
import net.papanketik.moviescatalogue.core.domain.repository.IMovieAndTvShowRepository
import net.papanketik.moviescatalogue.core.vo.Resource

class MovieAndTvShowInteractor(private val movieAndTvShowRepository: IMovieAndTvShowRepository): MovieAndTvShowUseCase {
    override fun getMovieData(): Flow<Resource<PagingData<MovieAndTvShow>>> = movieAndTvShowRepository.getMovieData()

    override fun getMovieDataById(movieId: Int): Flow<Resource<MovieAndTvShow>> = movieAndTvShowRepository.getMovieDataById(movieId)

    override fun getBookmarkedMovieData(): Flow<PagingData<MovieAndTvShow>> = movieAndTvShowRepository.getBookmarkedMovieData()

    override fun getTvShowData(): Flow<Resource<PagingData<MovieAndTvShow>>> = movieAndTvShowRepository.getTvShowData()

    override fun getTvShowDataById(tvShowId: Int): Flow<Resource<MovieAndTvShow>> = movieAndTvShowRepository.getTvShowDataById(tvShowId)

    override fun getBookmarkedTvShowData(): Flow<PagingData<MovieAndTvShow>> = movieAndTvShowRepository.getBookmarkedTvShowData()

    override fun setContentBookmark(content: MovieAndTvShow, state: Boolean) = movieAndTvShowRepository.setContentBookmark(content, state)

    override fun getMovieDataTest(): ArrayList<MovieAndTvShow> = movieAndTvShowRepository.getMovieDataTest()
}