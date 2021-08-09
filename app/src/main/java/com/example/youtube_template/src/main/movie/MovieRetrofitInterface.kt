package com.example.youtube_template.src.main.movie

import com.example.youtube_template.BuildConfig
import com.example.youtube_template.src.main.movie.models.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRetrofitInterface {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") api_key : String = BuildConfig.TMDB_API_KEY,
        @Query("language") language : String = "ko"
    ) : Call<Movies>

    @GET("movie/top_rated")
    fun getTopRatedMovie(
        @Query("api_key") api_key : String = BuildConfig.TMDB_API_KEY,
        @Query("language") language : String = "ko"
    ) : Call<Movies>

    @GET("movie/upcoming")
    fun getUpcomingMovie(
        @Query("api_key") api_key : String = BuildConfig.TMDB_API_KEY,
        @Query("language") language : String = "ko"
    ) : Call<Movies>
}