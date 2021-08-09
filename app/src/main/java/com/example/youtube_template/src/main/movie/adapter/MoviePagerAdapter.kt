package com.example.youtube_template.src.main.movie.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtube_template.databinding.ItemMoviePager2Binding
import com.example.youtube_template.src.main.movie.models.MovieMeta

class MoviePagerAdapter(private val context : Context) : RecyclerView.Adapter<MoviePagerAdapter.ViewHolder>(){

    // item layout 에 cardView 존재시 정상적으로 작동되지 않는다. 이유가 뭘까
    
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemMoviePager2Binding
    private val movieList = ArrayList<MovieMeta>()

    class ViewHolder(binding : ItemMoviePager2Binding) : RecyclerView.ViewHolder(binding.root){
        val movieTitle = binding.tvMovieTitle
        val moviePoster = binding.cv
        val movieInfo = binding.tvMovieInfo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMoviePager2Binding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = movieList[position].title
        holder.movieInfo.text = movieList[position].overview
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + movieList[position].poster_path).into(holder.moviePoster)
    }

    override fun getItemCount(): Int = movieList.size

    fun addMovieData(newMovieData : List<MovieMeta>) {
        movieList.addAll(newMovieData)
        notifyDataSetChanged()
    }
}