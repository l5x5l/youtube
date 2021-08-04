package com.example.youtube_template.src.main

import android.os.Bundle
import com.example.youtube_template.R
import com.example.youtube_template.config.BaseActivity
import com.example.youtube_template.databinding.ActivityMainBinding
import com.example.youtube_template.src.main.home.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val homeFragment : HomeFragment = HomeFragment()
    private var previousFragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(R.id.fragment_layout, homeFragment).commit()

        binding.bottom.setOnItemSelectedListener {

            supportFragmentManager.beginTransaction().hide(previousFragment).commit()

            when(it.itemId){
                R.id.bottom_home -> {
                    supportFragmentManager.beginTransaction().show(homeFragment).commit()
                    previousFragment = homeFragment
                }
            }
            true
        }
    }
}