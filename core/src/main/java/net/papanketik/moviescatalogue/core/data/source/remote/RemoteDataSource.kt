package net.papanketik.moviescatalogue.core.data.source.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.papanketik.moviescatalogue.core.BuildConfig
import net.papanketik.moviescatalogue.core.data.source.remote.network.ApiResponse
import net.papanketik.moviescatalogue.core.data.source.remote.response.*
import net.papanketik.moviescatalogue.core.data.source.remote.network.ApiService

class RemoteDataSource(private val apiService: ApiService) {
    private val apiKey = BuildConfig.tmdbApiKey

    suspend fun getMovieData(): Flow<ApiResponse<List<ResultsMovieItem>>> {
        return flow {
            try {
                val response = apiService.getMovie(apiKey)
                val results = response.results
                if (results.isNotEmpty()) {
                    emit(ApiResponse.Success(results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMovieDataById(movieId: Int): Flow<ApiResponse<DetailMovieResponse>> {
        return flow {
            try {
                val response = apiService.getMovieById(movieId, apiKey)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTvShowData(): Flow<ApiResponse<List<ResultsTvItem>>> {
        return flow {
            try {
                val response = apiService.getTvShow(apiKey)
                val results = response.results
                if (results.isNotEmpty()) {
                    emit(ApiResponse.Success(results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTvShowDataById(tvShowId: Int): Flow<ApiResponse<DetailTvResponse>> {
        return flow {
            try {
                val response = apiService.getTvShowById(tvShowId, apiKey)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}