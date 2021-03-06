package com.example.youtube_template.config

import android.app.Application
import android.content.SharedPreferences
import com.example.youtube_template.BuildConfig.KAKAO_API_KEY
import com.kakao.sdk.common.KakaoSdk
/*import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor*/
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/*import java.util.concurrent.TimeUnit*/

class GlobalApplication : Application() {

    private val youtubeApiUrl = "https://www.googleapis.com/youtube/v3/"
    private val tmdbApiUrl = "https://api.themoviedb.org/3/"

    companion object {
        lateinit var globalSharedPreferences: SharedPreferences
        const val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"
        lateinit var youtubeRetrofit: Retrofit
        lateinit var tmdbRetrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        initRetrofitInstance()
        globalSharedPreferences = applicationContext.getSharedPreferences("youtube_template", MODE_PRIVATE)
        KakaoSdk.init(this, KAKAO_API_KEY)
    }

    private fun initRetrofitInstance(){
/*        val youtubeClient = OkHttpClient.Builder().readTimeout(5000,TimeUnit.MILLISECONDS).connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).addNetworkInterceptor(XAccessTokenInterceptor()).build()*/

        youtubeRetrofit = Retrofit.Builder().baseUrl(youtubeApiUrl).addConverterFactory(GsonConverterFactory.create()).build()
        tmdbRetrofit = Retrofit.Builder().baseUrl(tmdbApiUrl).addConverterFactory(GsonConverterFactory.create()).build()
    }

}