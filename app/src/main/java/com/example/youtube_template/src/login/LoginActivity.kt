package com.example.youtube_template.src.login

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import com.example.youtube_template.R
import com.example.youtube_template.config.BaseActivity
import com.example.youtube_template.config.GlobalApplication
import com.example.youtube_template.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    private val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "login failure", error)
        } else if (token != null) {
            Log.i(TAG, "login success ${token.accessToken}")
            GlobalApplication.globalSharedPreferences.edit().putString("kakao", token.accessToken).apply()
            onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*        setContentView(R.layout.activity_login)*/

        binding.btnKakaoLogin.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.none, R.anim.vertical_out)
    }
}