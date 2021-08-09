package com.example.youtube_template.src.main.movie

import com.example.youtube_template.src.main.movie.models.MovieMeta

interface MovieFragmentView {
    fun onGetPopularMovieSuccess(movieList : List<MovieMeta>)

    fun onGetTopRatedMovieSuccess(movieList : List<MovieMeta>)

    fun onGetUpcomingMovieSuccess(movieList : List<MovieMeta>)

    fun onGetMovieFailure(message : String)

}