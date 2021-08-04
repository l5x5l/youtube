package com.example.youtube_template.config

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.youtube_template.util.LoadingDialog

abstract class BaseActivity<B : ViewBinding> (private val inflate : (LayoutInflater) -> B) : AppCompatActivity() {
    protected lateinit var binding : B
    private set
    lateinit var mLoadingDialog : LoadingDialog
    /*lateinit var mLoadingDialog : LoadingDialog*/


    // persistentState: PersistableBundle?
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    // 이거 context 를 굳이 인자로 받을 필요가 있나?
    fun showLoadingDialog(context : Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    fun showCustomToast(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun goToActivitySimple(activity : Activity){
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }
}