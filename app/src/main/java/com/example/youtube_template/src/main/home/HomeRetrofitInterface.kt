package com.example.youtube_template.src.main.home

import com.example.youtube_template.BuildConfig
import com.example.youtube_template.src.main.home.models.Categories
import com.example.youtube_template.src.main.home.models.Channels
import com.example.youtube_template.src.main.home.models.Videos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeRetrofitInterface {
    @GET("videos")
    fun getVideosPopular(
        @Query("key") key : String = BuildConfig.GOOGLE_API_KEY,
        @Query("part") part : String = "snippet, statistics",
        @Query("chart") chart : String = "mostPopular",
        @Query("regionCode") regionCode : String = "KR",
        @Query("maxResults") maxResults : Int = 10
    ) : Call<Videos>

    @GET("channels")
    fun getChannelProfile(
        @Query("key") key : String = BuildConfig.GOOGLE_API_KEY,
        @Query("part") part : String = "snippet",
        @Query("id") id : String
    ) : Call<Channels>

    @GET("videoCategories")
    fun getVideoCategories(
        @Query("key") key : String = BuildConfig.GOOGLE_API_KEY,
        @Query("regionCode") regionCode: String = "KR",
        @Query("hl") hl : String = "KO"
    ) : Call<Categories>
}