package com.example.youtube_template.src.main.home

import com.example.youtube_template.src.main.home.models.Categories
import com.example.youtube_template.src.main.home.models.Channels
import com.example.youtube_template.src.main.home.models.VideoMeta
import com.example.youtube_template.src.main.home.models.Videos

interface HomeFragmentView {
    fun onGetVideoSuccess(videoList : List<VideoMeta>, channelString : String, nextToken : String? = null)

    fun onGetVideoFailure(message : String)

    fun onGetUserSuccess(map : Map<String, String>)

    fun onGetUserFailure(message: String)

    fun onGetCategorySuccess(response : Categories)

    fun onGetCategoryFailure(message: String)
}