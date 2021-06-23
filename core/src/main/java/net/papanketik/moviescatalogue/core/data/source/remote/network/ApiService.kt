package net.papanketik.moviescatalogue.core.data.source.remote.network

import net.papanketik.moviescatalogue.core.data.source.remote.response.DetailMovieResponse
import net.papanketik.moviescatalogue.core.data.source.remote.response.DetailTvResponse
import net.papanketik.moviescatalogue.core.data.source.remote.response.MovieResponse
import net.papanketik.moviescatalogue.core.data.source.remote.response.TvResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/discover/movie?sort_by=popularity.desc")
    suspend fun getMovie(
        @Query("api_key") apikey: String
    ): MovieResponse

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apikey: String
    ): DetailMovieResponse

    @GET("/3/discover/tv?sort_by=popularity.desc")
    suspend fun getTvShow(
        @Query("api_key") apikey: String
    ): TvResponse

    @GET("/3/tv/{tv_id}")
    suspend fun getTvShowById(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apikey: String
    ): DetailTvResponse
}