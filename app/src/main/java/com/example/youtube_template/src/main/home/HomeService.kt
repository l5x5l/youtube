package com.example.youtube_template.src.main.home

import com.example.youtube_template.config.GlobalApplication
import com.example.youtube_template.src.main.home.models.Categories
import com.example.youtube_template.src.main.home.models.Channels
import com.example.youtube_template.src.main.home.models.Videos
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService(val view : HomeFragmentView) {
    private val homeRetrofitInterface = GlobalApplication.youtubeRetrofit.create(HomeRetrofitInterface::class.java)

    fun tryGetVideos(nextToken : String ? = null) {
        //val homeRetrofitInterface = GlobalApplication.youtubeRetrofit.create(HomeRetrofitInterface::class.java)
        homeRetrofitInterface.getVideosPopular(pageToken = nextToken).enqueue(object: Callback<Videos>{
            override fun onResponse(call: Call<Videos>, response: Response<Videos>) {
                if (response.isSuccessful){
                    val tempVideoList = response.body()?.items
                    val nextToken = response.body()?.nextPageToken

                    var channelString = ""
                    for (i in tempVideoList!!.indices){
                        if (i != 0){
                            channelString += ","
                        }
                        channelString += tempVideoList!![i].snippet.channelId
                    }

                    view.onGetVideoSuccess(tempVideoList, channelString, nextToken)
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
        homeRetrofitInterface.getChannelProfile(id = channelId).enqueue(object: Callback<Channels>{
            override fun onResponse(call: Call<Channels>, response: Response<Channels>) {
                if (response.isSuccessful){
                    val channelProfileList = response.body()?.items
                    val map = mutableMapOf<String, String>()
                    for (channelProfile in channelProfileList!!){
                        map[channelProfile.id] = channelProfile.snippet.thumbnails.default.url
                    }
                    view.onGetUserSuccess(map)
                } else {
                    view.onGetUserFailure("get response is failure")
                }
            }

            override fun onFailure(call: Call<Channels>, t: Throwable) {
                view.onGetUserFailure("onFailure")
            }

        })
    }

    fun tryGetCategories(){
        homeRetrofitInterface.getVideoCategories().enqueue(object: Callback<Categories>{
            override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                if (response.isSuccessful){
                    view.onGetCategorySuccess(response.body()!!)
                } else {
                    view.onGetCategoryFailure("get response is failure")
                }
            }

            override fun onFailure(call: Call<Categories>, t: Throwable) {
                view.onGetCategoryFailure("onFailure")
            }

        })
    }

}