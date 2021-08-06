package com.example.youtube_template.src.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube_template.databinding.ItemSearchTextBinding
import com.example.youtube_template.src.main.MainActivity

class SearchViewAdapter(private val context : Context) : RecyclerView.Adapter<SearchViewAdapter.ViewHolder>(){

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding : ItemSearchTextBinding
    private var dataList = listOf<String>()

    class ViewHolder(binding : ItemSearchTextBinding) : RecyclerView.ViewHolder(binding.root) {
        val mainLayout = binding.mainLayout
        val searchText = binding.tvSearch
        val applyBtn = binding.icApply
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemSearchTextBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mainLayout.setOnClickListener {  }
        holder.applyBtn.setOnClickListener {
            (context as MainActivity).setEditText(dataList[position])
        }
        holder.searchText.text = dataList[position]
    }

    override fun getItemCount(): Int = dataList.size

    fun searchDataChanged(newDataList : List<String>){
        dataList = newDataList
        notifyDataSetChanged()
    }
}