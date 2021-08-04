package com.example.youtube_template.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.youtube_template.util.LoadingDialog

abstract class BaseFragment<B : ViewBinding>(
    private val bind : (View) -> B, @LayoutRes layoutResId : Int
) : Fragment(layoutResId) {
    private var _binding : B? = null
    protected val binding get() = _binding!!
    private lateinit var mLoadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showCustomToast(message : String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    // 이거 context 대신 activity 쓰면 안되나?
    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing){
            mLoadingDialog.dismiss()
        }
    }
}