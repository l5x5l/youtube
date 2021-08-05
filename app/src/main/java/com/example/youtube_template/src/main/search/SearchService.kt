package com.example.youtube_template.src.main.search

import com.example.youtube_template.config.GlobalApplication
import com.example.youtube_template.src.main.search.models.Channels
import com.example.youtube_template.src.main.search.models.Videos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val view : SearchFragmentView) {
    private val searchRetrofitInterface = GlobalApplication.youtubeRetrofit.create(SearchRetrofitInterface::class.java)
    fun tryGetVideos() {
        //val homeRetrofitInterface = GlobalApplication.youtubeRetrofit.create(HomeRetrofitInterface::class.java)
        searchRetrofitInterface.getVideosPopular().enqueue(object: Callback<Videos> {
            override fun onResponse(call: Call<Videos>, response: Response<Videos>) {
                if (response.isSuccessful){
                    view.onGetVideoSuccess(response.body()!!)
                } else {
                    view.onGetVideoFailure("get response is failure")
                }
            }

            override fun onFailure(call: Call<Videos>, t: Throwable) {
                view.onGetVideoFailure("onFailure")
            }

        })
    }

    fun tryGetProfile(channelId: String) {
        searchRetrofitInterface.getChannelProfile(id = channelId).enqueue(object: Callback<Channels> {
            override fun onResponse(call: Call<Channels>, response: Response<Channels>) {
                if (response.isSuccessful){
                    view.onGetUserSuccess(response.body()!!)
                } else {
                    view.onGetUserFailure("get response is failure")
                }
            }

            override fun onFailure(call: Call<Channels>, t: Throwable) {
                view.onGetUserFailure("onFailure")
            }

        })
    }
}