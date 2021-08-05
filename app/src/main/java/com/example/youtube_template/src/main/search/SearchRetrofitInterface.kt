package com.example.youtube_template.src.main.search

import com.example.youtube_template.BuildConfig
import com.example.youtube_template.src.main.search.models.Channels
import com.example.youtube_template.src.main.search.models.Videos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRetrofitInterface {
    @GET("videos")
    fun getVideosPopular(
            @Query("key") key : String = BuildConfig.GOOGLE_API_KEY,
            @Query("part") part : String = "snippet, statistics",
            @Query("chart") chart : String = "mostPopular",
            @Query("regionCode") regionCode : String = "KR",
            @Query("maxResults") maxResults : Int = 10,
            @Query("videoCategoryId") videoCategoryId : String = "0"
    ) : Call<Videos>

    @GET("channels")
    fun getChannelProfile(
            @Query("key") key : String = BuildConfig.GOOGLE_API_KEY,
            @Query("part") part : String = "snippet",
            @Query("id") id : String
    ) : Call<Channels>
}