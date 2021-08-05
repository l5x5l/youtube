package com.example.youtube_template.src.main.search

import com.example.youtube_template.src.main.search.models.Channels
import com.example.youtube_template.src.main.search.models.Videos

interface SearchFragmentView {
    fun onGetVideoSuccess(response : Videos)

    fun onGetVideoFailure(message : String)

    fun onGetUserSuccess(response : Channels)

    fun onGetUserFailure(message: String)
}