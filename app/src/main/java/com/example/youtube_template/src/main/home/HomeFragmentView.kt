package com.example.youtube_template.src.main.home

import com.example.youtube_template.src.main.home.models.Categories
import com.example.youtube_template.src.main.home.models.VideoMeta

interface HomeFragmentView {
    fun onGetVideoSuccess(videoList : List<VideoMeta>, channelString : String, nextToken : String? = null)

    fun onGetVideoFailure(message : String)

    fun onGetUserSuccess(map : Map<String, String>)

    fun onGetUserFailure(message: String)

    fun onGetCategorySuccess(response : Categories)

    fun onGetCategoryFailure(message: String)
}