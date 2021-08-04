package com.example.youtube_template.src.main.home

import com.example.youtube_template.src.main.home.models.Categories
import com.example.youtube_template.src.main.home.models.Channels
import com.example.youtube_template.src.main.home.models.Videos

interface HomeFragmentView {
    fun onGetVideoSuccess(response : Videos)

    fun onGetVideoFailure(message : String)

    fun onGetUserSuccess(response : Channels)

    fun onGetUserFailure(message: String)

    fun onGetCategorySuccess(response : Categories)

    fun onGetCategoryFailure(message: String)
}