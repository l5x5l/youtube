package com.example.youtube_template.src.main.home

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube_template.R
import com.example.youtube_template.config.BaseFragment
import com.example.youtube_template.config.GlobalApplication
import com.example.youtube_template.databinding.FragmentHomeBinding
import com.example.youtube_template.src.login.LoginActivity
import com.example.youtube_template.src.main.MainActivity
import com.example.youtube_template.src.main.home.adapter.VideoAdapter
import com.example.youtube_template.src.main.home.models.Categories
import com.example.youtube_template.src.main.home.models.VideoMeta
import com.google.android.material.chip.Chip
import com.kakao.sdk.user.UserApiClient

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home), HomeFragmentView {

    private val homeService = HomeService(this)
    private var tempVideoList : List<VideoMeta> ?= null
    private var categoryMap = mutableMapOf<String, String>()
    private var nextPageToken : String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.appbar.setLogo(R.drawable.youtube_mini)
        (activity as MainActivity).setSupportActionBar(binding.toolbar.appbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)

        val linearLayoutManager = LinearLayoutManager(activity as MainActivity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = linearLayoutManager

        binding.recyclerView.adapter = VideoAdapter(activity as MainActivity)
        binding.recyclerView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.recyclerView.canScrollVertically(1)){
                    homeService.tryGetVideos(nextToken = nextPageToken)
                    //Log.d("recyclerview", "is bottom!!")
                }
            }
        })

        binding.refreshLayout.setOnRefreshListener {
            (binding.recyclerView.adapter as VideoAdapter).clearDataList()
            nextPageToken = null
            homeService.tryGetVideos(nextToken = nextPageToken)
        }

/*        binding.refreshLayout.setOnRefreshListener(object:RecyclerRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                (binding.recyclerView.adapter as VideoAdapter).clearDataList()
                nextPageToken = null
                homeService.tryGetVideos(nextToken = nextPageToken)
            }
        })*/

        showLoadingDialog(requireContext())
        homeService.tryGetCategories()
        homeService.tryGetVideos()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_basic, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.appbar_search -> {
                (activity as MainActivity).setSearchLayoutVisible()
                true
            }
            R.id.appbar_user -> {
                if (GlobalApplication.globalSharedPreferences.getString("kakao", "__null__") == "__null__"){
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.overridePendingTransition(R.anim.vertical_in, R.anim.none)
                } else {
                    UserApiClient.instance.logout { error ->
                        if (error != null) {
                            Log.e(TAG, "Logout Failure, remove AccessToken from SDK", error)
                        }
                        else {
                            Log.i(TAG, "Logout Success, remove AccessToken from SDK")
                            showCustomToast("remove Kakao Access Token")
                        }
                    }
                    GlobalApplication.globalSharedPreferences.edit().putString("kakao", "__null__").apply()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onGetVideoSuccess(videoList : List<VideoMeta>, channelString : String, nextToken : String?) {
        tempVideoList = videoList
        nextPageToken = nextToken
        homeService.tryGetProfile(channelString)
    }

    override fun onGetVideoFailure(message: String) {
        dismissLoadingDialog()
        binding.refreshLayout.setRefreshing(false)
        Log.d("onGetVideoFailure", message)
    }

    override fun onGetUserSuccess(map : Map<String, String>) {
        (binding.recyclerView.adapter as VideoAdapter).addDataList(tempVideoList!!, map)
        dismissLoadingDialog()
        binding.refreshLayout.setRefreshing(false)
    }

    override fun onGetUserFailure(message: String) {
        dismissLoadingDialog()
        binding.refreshLayout.setRefreshing(false)
        Log.d("onGetUserFailure", message)
    }

    override fun onGetCategorySuccess(response: Categories) {
        val categories = response.items
        for (category in categories) {
            categoryMap[category.snippet.title] = category.id
            val chip = Chip(activity as MainActivity)
            chip.text = category.snippet.title
            chip.isCheckable = true
            chip.isCheckedIconVisible = false
            binding.toolbar.chipGroupHome.addView(chip)
        }
    }

    override fun onGetCategoryFailure(message: String) {
        Log.d("onGetCategoryFailure", message)
    }


}