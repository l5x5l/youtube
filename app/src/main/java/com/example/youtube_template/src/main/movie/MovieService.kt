package com.example.youtube_template.src.main.movie

import com.example.youtube_template.config.GlobalApplication
import com.example.youtube_template.src.main.movie.models.Movies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieService(val view : MovieFragmentView) {
    private val movieRetrofitInterface = GlobalApplication.tmdbRetrofit.create(MovieRetrofitInterface::class.java)

    fun tryGetPopularMovies(){
        movieRetrofitInterface.getPopularMovies().enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if(response.isSuccessful) {
                    val tempMovieList = response.body()?.results
                    view.onGetPopularMovieSuccess(tempMovieList!!)
                } else {
                    view.onGetMovieFailure("getPopularMovie response is not successful")
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                view.onGetMovieFailure("getPopularMovie is failure")
            }

        })
    }

    fun tryGetTopRatedMovies() {
        movieRetrofitInterface.getTopRatedMovie().enqueue(object : Callback<Movies>{
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if(response.isSuccessful) {
                    val tempMovieList = response.body()?.results
                    view.onGetTopRatedMovieSuccess(tempMovieList!!)
                } else {
                    view.onGetMovieFailure("getTopRatedMovie response is not successful")
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                view.onGetMovieFailure("getTopRatedMovie is failure")
            }
        })
    }

    fun tryGetUpcomingMovies() {
        movieRetrofitInterface.getUpcomingMovie().enqueue(object:Callback<Movies>{
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful){
                    val tempMovieList = response.body()?.results
                    view.onGetUpcomingMovieSuccess(tempMovieList!!)
                } else {
                    view.onGetMovieFailure("getUpcomingMovie is failure")
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                view.onGetMovieFailure("getUpcomingMovie is failure")
            }

        })
    }
}