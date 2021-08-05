package com.example.youtube_template.src.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.youtube_template.R
import com.example.youtube_template.config.BaseActivity
import com.example.youtube_template.databinding.ActivityMainBinding
import com.example.youtube_template.src.main.home.HomeFragment
import com.example.youtube_template.src.main.search.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val homeFragment : Fragment = HomeFragment()
    private var searchFragment : SearchFragment ?= null
    private var previousFragment = homeFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(binding.fragmentLayout.id, homeFragment).commit()

        binding.bottom.setOnItemSelectedListener {

            supportFragmentManager.beginTransaction().hide(previousFragment).commit()

            when(it.itemId){
                R.id.bottom_home -> {
                    supportFragmentManager.beginTransaction().show(homeFragment).commit()
                    previousFragment = homeFragment
                }
                R.id.bottom_search -> {
                    if (searchFragment == null) {
                        searchFragment = SearchFragment()
                        supportFragmentManager.beginTransaction().add(binding.fragmentLayout.id, searchFragment!!).commit()
                    } else {
                        supportFragmentManager.beginTransaction().show(searchFragment!!).commit()
                    }
                    previousFragment = searchFragment!!
                }
            }
            true
        }
    }
}