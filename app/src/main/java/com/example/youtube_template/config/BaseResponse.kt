package com.example.youtube_template.config

import com.google.gson.annotations.SerializedName

// api 의 response 에 따라 수정해서 사용해야 할듯
open class BaseResponse (
    @SerializedName("isSuccess") val isSuccess : Boolean = false,
    @SerializedName("code") val code : Int = 0,
    @SerializedName("message") val message : String? = null
)

