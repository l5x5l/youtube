package com.example.youtube_template.src.main.home

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube_template.R
import com.example.youtube_template.config.BaseFragment
import com.example.youtube_template.databinding.FragmentHomeBinding
import com.example.youtube_template.src.main.MainActivity
import com.example.youtube_template.src.main.home.adapter.VideoAdapter
import com.example.youtube_template.src.main.home.models.Categories
import com.example.youtube_template.src.main.home.models.Channels
import com.example.youtube_template.src.main.home.models.VideoMeta
import com.example.youtube_template.src.main.home.models.Videos
import com.google.android.material.chip.Chip

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), HomeFragmentView {

    private val homeService = HomeService(this)
    private var tempVideoList : List<VideoMeta> ?= null
    private var categoryMap = mutableMapOf<String, String>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.appbar.setLogo(R.drawable.youtube_mini)
        (activity as MainActivity).setSupportActionBar(binding.appbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)

        val linearLayoutManager = LinearLayoutManager(activity as MainActivity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = linearLayoutManager

        binding.recyclerView.adapter = VideoAdapter(activity as MainActivity, listOf<VideoMeta>())
        showLoadingDialog(requireContext())
        homeService.tryGetCategories()
        homeService.tryGetVideos()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_basic, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onGetVideoSuccess(response: Videos) {
        tempVideoList = response.items ?: listOf()

        var channelString = ""
        for (i in tempVideoList!!.indices){
            if (i != 0){
                channelString += ","
            }
            channelString += tempVideoList!![i].snippet.channelId
        }
        homeService.tryGetProfile(channelString)
    }

    override fun onGetVideoFailure(message: String) {
        dismissLoadingDialog()
        Log.d("onGetVideoFailure", message)
    }

    override fun onGetUserSuccess(response: Channels) {
        val channelProfileList = response.items
        val map = mutableMapOf<String, String>()

        for (channelProfile in channelProfileList){
            map[channelProfile.id] = channelProfile.snippet.thumbnails.default.url
        }
        (binding.recyclerView.adapter as VideoAdapter).changeDataList(tempVideoList!!, map)
        dismissLoadingDialog()
    }

    override fun onGetUserFailure(message: String) {
        dismissLoadingDialog()
        Log.d("onGetUserFailure", message)
    }

    override fun onGetCategorySuccess(response: Categories) {
        val categories = response.items
        for (category in categories) {
            categoryMap[category.snippet.title] = category.id
            val chip = Chip(activity as MainActivity)
            chip.text = category.snippet.title
            chip.isCheckable = true
            chip.isCheckedIconVisible = false
            binding.chipGroupHome.addView(chip)
        }
    }

    override fun onGetCategoryFailure(message: String) {
        Log.d("onGetCategoryFailure", message)
    }


}