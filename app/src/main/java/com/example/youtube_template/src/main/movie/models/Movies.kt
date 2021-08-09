package com.example.youtube_template.src.main.movie.models

data class MovieMeta(val title : String = "__DEFAULT__", val poster_path : String = "__DEFAULT__", val overview : String = "__DEFAULT__")
data class Movies(val results : List<MovieMeta>)