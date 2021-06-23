package net.papanketik.moviescatalogue.core.data

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.papanketik.moviescatalogue.core.data.source.local.LocalDataSource
import net.papanketik.moviescatalogue.core.data.source.local.entity.MovieAndTvShowEntity
import net.papanketik.moviescatalogue.core.data.source.remote.RemoteDataSource
import net.papanketik.moviescatalogue.core.data.source.remote.network.ApiResponse
import net.papanketik.moviescatalogue.core.data.source.remote.response.DetailMovieResponse
import net.papanketik.moviescatalogue.core.data.source.remote.response.DetailTvResponse
import net.papanketik.moviescatalogue.core.data.source.remote.response.ResultsMovieItem
import net.papanketik.moviescatalogue.core.data.source.remote.response.ResultsTvItem
import net.papanketik.moviescatalogue.core.domain.model.MovieAndTvShow
import net.papanketik.moviescatalogue.core.domain.repository.IMovieAndTvShowRepository
import net.papanketik.moviescatalogue.core.utils.AppExecutors
import net.papanketik.moviescatalogue.core.utils.DataMapper
import net.papanketik.moviescatalogue.core.vo.Resource

class MovieAndTvShowRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors): IMovieAndTvShowRepository {

    override fun getMovieData(): Flow<Resource<PagingData<MovieAndTvShow>>> {
        return object : NetworkBoundResource<PagingData<MovieAndTvShow>, List<ResultsMovieItem>>() {
            override fun loadFromDB(): Flow<PagingData<MovieAndTvShow>> {
                return Pager(
                    PagingConfig(
                        pageSize = 4,
                        enablePlaceholders = false,
                        initialLoadSize = 4
                    )
                ) {
                    localDataSource.getMovieData()
                }.flow
                    .map { pagingData ->
                        DataMapper.mapEntitiesToDomain(pagingData)
                    }
            }

            override fun shouldFetch(data: PagingData<MovieAndTvShow>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsMovieItem>>> {
                return remoteDataSource.getMovieData()
            }

            override suspend fun saveCallResult(data: List<ResultsMovieItem>?) {
                val moviesList = ArrayList<MovieAndTvShowEntity>()
                if (data != null) {
                    moviesList.addAll(DataMapper.mapMovieResponsesToEntities(data))
                }
                localDataSource.insertContents(moviesList)
            }

        }.asFlow()
    }

    override fun getMovieDataById(movieId: Int): Flow<Resource<MovieAndTvShow>> {
        return object : NetworkBoundResource<MovieAndTvShow, DetailMovieResponse>() {
            override fun loadFromDB(): Flow<MovieAndTvShow> {
                return localDataSource.getMovieDataById(movieId).map {
                    DataMapper.mapDetailEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: MovieAndTvShow?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> {
                return remoteDataSource.getMovieDataById(movieId)
            }

            override suspend fun saveCallResult(data: DetailMovieResponse?) {
                if (data != null) {
                    val movieData = DataMapper.mapDetailMovieResponseToEntity(data)
                    localDataSource.updateContent(movieData)
                }
            }
        }.asFlow()
    }

    override fun getBookmarkedMovieData(): Flow<PagingData<MovieAndTvShow>> {
        return Pager(
            PagingConfig(
                pageSize = 4,
                enablePlaceholders = false,
                initialLoadSize = 4
            )
        ) {
            localDataSource.getBookmarkedMovieData()
        }.flow
            .map { pagingData ->
                DataMapper.mapEntitiesToDomain(pagingData)
            }
    }

    override fun getTvShowData(): Flow<Resource<PagingData<MovieAndTvShow>>> {
        return object : NetworkBoundResource<PagingData<MovieAndTvShow>, List<ResultsTvItem>>() {
            override fun loadFromDB(): Flow<PagingData<MovieAndTvShow>> {
                return Pager(
                    PagingConfig(
                        pageSize = 4,
                        enablePlaceholders = false,
                        initialLoadSize = 4
                    )
                ) {
                    localDataSource.getTvShowData()
                }.flow
                    .map { pagingData ->
                        DataMapper.mapEntitiesToDomain(pagingData)
                    }
            }

            override fun shouldFetch(data: PagingData<MovieAndTvShow>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsTvItem>>> {
                return remoteDataSource.getTvShowData()
            }

            override suspend fun saveCallResult(data: List<ResultsTvItem>?) {
                val tvShowList = ArrayList<MovieAndTvShowEntity>()
                if (data != null) {
                    tvShowList.addAll(DataMapper.mapTvShowResponsesToEntities(data))
                }
                localDataSource.insertContents(tvShowList)
            }
        }.asFlow()
    }

    override fun getTvShowDataById(tvShowId: Int): Flow<Resource<MovieAndTvShow>> {
        return object : NetworkBoundResource<MovieAndTvShow, DetailTvResponse>() {
            override fun loadFromDB(): Flow<MovieAndTvShow> {
                return localDataSource.getTvShowDataById(tvShowId).map {
                    DataMapper.mapDetailEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: MovieAndTvShow?): Boolean {
                return data == null
            }

            override suspend fun createCall(): Flow<ApiResponse<DetailTvResponse>> {
                return remoteDataSource.getTvShowDataById(tvShowId)
            }

            override suspend fun saveCallResult(data: DetailTvResponse?) {
                if (data != null) {
                    val tvShowData = DataMapper.mapDetailTvShowResponseToEntity(data)
                    localDataSource.updateContent(tvShowData)
                }
            }
        }.asFlow()
    }

    override fun getBookmarkedTvShowData(): Flow<PagingData<MovieAndTvShow>> {
        return Pager(
            PagingConfig(
                pageSize = 4,
                enablePlaceholders = false,
                initialLoadSize = 4
            )
        ) {
            localDataSource.getBookmarkedTvShowData()
        }.flow
            .map { pagingData ->
                DataMapper.mapEntitiesToDomain(pagingData)
            }
    }

    override fun setContentBookmark(content: MovieAndTvShow, state: Boolean) {
        return appExecutors.diskIO().execute { localDataSource.setBookmarkContent(DataMapper.mapDomainToEntity(content), state) }
    }

    override fun getMovieDataTest(): ArrayList<MovieAndTvShow> {
        val movieData = ArrayList<MovieAndTvShow>()
        movieData.add(DataMapper.mapEntityToDomain(MovieAndTvShowEntity(0, "", "", 0.0, "", "", 0)))
        return movieData
    }

    companion object {
        const val MOVIE_CONTENT_TYPE = 0
        const val TV_SHOW_CONTENT_TYPE = 1
    }
}