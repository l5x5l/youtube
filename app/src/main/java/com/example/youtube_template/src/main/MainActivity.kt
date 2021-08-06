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

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val homeFragment : Fragment = HomeFragment()
    private var searchFragment : SearchFragment ?= null
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
            }
            true
        }

        // searchLayout 부분
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
        imm.showSoftInput(window.decorView.rootView, InputMethodManager.SHOW_IMPLICIT)
        binding.searchLayout.visibility = View.VISIBLE
    }

    private fun setSearchLayoutGone() {
        imm.hideSoftInputFromWindow(window.decorView.rootView.windowToken, 0)
        binding.searchLayout.visibility = View.GONE
    }

    // SearchViewAdapter 에서 사용
    fun setEditText(text : String) {
         binding.etSearch.setText(text)
    }

    private val textWatcher = object:TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val temp = listOf("노트북", "캠핑", "여행", "축구", "게임", binding.etSearch.text.toString())
            (binding.recyclerSearchText.adapter as SearchViewAdapter).searchDataChanged(temp)
        }

        override fun afterTextChanged(s: Editable?) {
            // do nothing?
        }

    }
}