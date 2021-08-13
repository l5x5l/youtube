package com.example.youtube_template.src.main.movie.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtube_template.R
import com.example.youtube_template.databinding.ItemMovieRecyclerBinding
import com.example.youtube_template.src.main.movie.models.MovieMeta

class MovieAdapterRecycler(private val context : Context) : RecyclerView.Adapter<MovieAdapterRecycler.ViewHolder>(){

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private val movieList = ArrayList<MovieMeta>()
    private lateinit var binding : ItemMovieRecyclerBinding

    class ViewHolder(val binding : ItemMovieRecyclerBinding) : RecyclerView.ViewHolder(binding.root){
        var poster = binding.poster
        var title = binding.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMovieRecyclerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.poster.setBackgroundColor(Color.TRANSPARENT)
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + movieList[position].poster_path).into(holder.poster)
        holder.title.setBackgroundColor(Color.TRANSPARENT)
        holder.title.text = movieList[position].title
    }

    override fun getItemCount(): Int = movieList.size

    fun addMovieData(newMovieData : List<MovieMeta>) {
        movieList.addAll(newMovieData)
        notifyDataSetChanged()
    }
}