package com.example.youtube_template.src.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtube_template.R
import com.example.youtube_template.config.BaseActivity
import com.example.youtube_template.databinding.ActivityMainBinding
import com.example.youtube_template.src.main.adapter.SearchViewAdapter
import com.example.youtube_template.src.main.home.HomeFragment
import com.example.youtube_template.src.main.search.SearchFragment
import com.example.youtube_template.src.main.movie.MovieFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val homeFragment : Fragment = HomeFragment()
    private var searchFragment : SearchFragment ?= null
    private var movieFragment : MovieFragment ?= null
    private var previousFragment = homeFragment
    private lateinit var imm : InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

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
                R.id.bottom_movie -> {
                    if (movieFragment == null){
                        movieFragment = MovieFragment()
                        supportFragmentManager.beginTransaction().add(binding.fragmentLayout.id, movieFragment!!).commit()
                    } else {
                        supportFragmentManager.beginTransaction().show(movieFragment!!).commit()
                    }
                    previousFragment = movieFragment!!
                }
            }
            true
        }

        // searchLayout ??????
        binding.recyclerSearchText.layoutManager = LinearLayoutManager(this)
        binding.recyclerSearchText.adapter = SearchViewAdapter(this)

        binding.btnSearchCancel.setOnClickListener {
            setSearchLayoutGone()
        }

        binding.etSearch.setOnEditorActionListener(object: TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d("MainActivity", binding.etSearch.text.toString())
                    return true
                }
                return false
            }
        })

        binding.etSearch.addTextChangedListener(textWatcher)

    }

    fun setSearchLayoutVisible() {
        binding.searchLayout.visibility = View.VISIBLE
        binding.etSearch.requestFocus()
/*        imm.showSoftInput(window.decorView.rootView, InputMethodManager.SHOW_IMPLICIT)*/
        imm.showSoftInput(binding.etSearch, 0)
    }

    private fun setSearchLayoutGone() {
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
        binding.searchLayout.visibility = View.GONE
        binding.etSearch.text?.clear()
        val temp = listOf<String>()
        (binding.recyclerSearchText.adapter as SearchViewAdapter).searchDataChanged(temp)
    }

    // SearchViewAdapter ?????? ??????
    fun setEditText(text : String) {
         binding.etSearch.setText(text)
    }

    private val textWatcher = object:TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val temp = listOf("?????????", "??????", "??????", "??????", "??????", "?????? : " + binding.etSearch.text.toString())
            (binding.recyclerSearchText.adapter as SearchViewAdapter).searchDataChanged(temp)
        }

        override fun afterTextChanged(s: Editable?) {
            // do nothing?
        }

    }
}