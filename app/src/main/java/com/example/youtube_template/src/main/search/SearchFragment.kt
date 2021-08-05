package com.example.youtube_template.src.main.search

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube_template.R
import com.example.youtube_template.config.BaseFragment
import com.example.youtube_template.databinding.FragmentSearchBinding
import com.example.youtube_template.src.main.MainActivity
import com.example.youtube_template.src.main.search.adapter.CategoryAdapter
import com.example.youtube_template.src.main.search.adapter.CategoryDecoration
import com.example.youtube_template.src.main.search.adapter.VideoAdapter
import com.example.youtube_template.src.main.search.models.VideoMeta
import com.example.youtube_template.src.main.search.models.Channels
import com.example.youtube_template.src.main.search.models.SearchCategory
import com.example.youtube_template.src.main.search.models.Videos


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), SearchFragmentView {

    private val searchService = SearchService(this)
    private var tempVideoList : List<VideoMeta> ?= null
    private var categoryMap = mutableMapOf<String, String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var categoryList = ArrayList<SearchCategory>()
        categoryList.add(SearchCategory("인기", R.drawable.background_hot, R.drawable.ic_fire))
        categoryList.add(SearchCategory("음악", R.drawable.background_music, R.drawable.ic_music))
        categoryList.add(SearchCategory("게임", R.drawable.background_game, R.drawable.ic_game_controller))
        categoryList.add(SearchCategory("영화", R.drawable.background_movie, R.drawable.ic_film))
        categoryList.add(SearchCategory("학습", R.drawable.background_study, R.drawable.ic_bulb))
        categoryList.add(SearchCategory("스포츠", R.drawable.background_exercise, R.drawable.ic_sport))

        val gridLayoutManager = GridLayoutManager(activity as MainActivity, 2)
        binding.categoryRecycler.layoutManager = gridLayoutManager

        binding.categoryRecycler.adapter = CategoryAdapter(activity as MainActivity, categoryList)
        binding.categoryRecycler.addItemDecoration(CategoryDecoration(activity as MainActivity))

        binding.videoRecycler.layoutManager = LinearLayoutManager(activity as MainActivity)
        binding.videoRecycler.adapter = VideoAdapter(activity as MainActivity, listOf<VideoMeta>())
        binding.videoRecycler.isNestedScrollingEnabled = false

        binding.appbar.setLogo(R.drawable.youtube_mini)
        (activity as MainActivity).setSupportActionBar(binding.appbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)
        showLoadingDialog(requireContext())
        searchService.tryGetVideos()
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
        searchService.tryGetProfile(channelString)
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
        (binding.videoRecycler.adapter as VideoAdapter).changeDataList(tempVideoList!!, map)
        dismissLoadingDialog()
    }

    override fun onGetUserFailure(message: String) {
        dismissLoadingDialog()
        Log.d("onGetUserFailure", message)
    }
}