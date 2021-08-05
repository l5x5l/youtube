package com.example.youtube_template.src.main.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube_template.databinding.ItemCategoryBinding
import com.example.youtube_template.src.main.search.models.SearchCategory

class CategoryAdapter (private val context : Context, private var dataList : List<SearchCategory>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    private lateinit var binding : ItemCategoryBinding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    class ViewHolder(binding : ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val categoryTitle = binding.tvCategory
        val categoryIcon = binding.icCategory
        val backgroundImg = binding.background
        val mainLayout = binding.mainLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryTitle.text = dataList[position].name
        holder.categoryIcon.setImageResource(dataList[position].Icon)
        holder.backgroundImg.setImageResource(dataList[position].backgroundSrc)
        holder.mainLayout.setOnClickListener{
            // movie fragment 로 이동
        }
    }

    override fun getItemCount(): Int = dataList.size
}