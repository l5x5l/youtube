package com.example.youtube_template.src.main.movie

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.youtube_template.R
import com.example.youtube_template.config.BaseFragment
import com.example.youtube_template.databinding.FragmentMovieBinding
import com.example.youtube_template.src.main.MainActivity
import com.example.youtube_template.src.main.movie.adapter.MovieAdapterRecycler
import com.example.youtube_template.src.main.movie.adapter.MoviePagerAdapter
import com.example.youtube_template.src.main.movie.models.MovieMeta


class MovieFragment : BaseFragment<FragmentMovieBinding>(FragmentMovieBinding::bind, R.layout.fragment_movie), MovieFragmentView{

    private val movieService = MovieService(this)
    private var isRunning = false
    private lateinit var viewPagerThread : Thread
    private val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setSupportActionBar(binding.toolbar.movieAppbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.viewPager.adapter = MoviePagerAdapter(activity as MainActivity)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvViewpagerCount.text = getString(R.string.movie_viewpager_count, position + 1, (binding.viewPager.adapter as MoviePagerAdapter).itemCount)
            }
        })

        val linearLayoutManager = LinearLayoutManager(activity as MainActivity)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerMoviePopular.layoutManager = linearLayoutManager
        binding.recyclerMoviePopular.adapter = MovieAdapterRecycler(activity as MainActivity)

        val linearLayoutManager2 = LinearLayoutManager(activity as MainActivity)
        linearLayoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerMovieTopRate.layoutManager = linearLayoutManager2
        binding.recyclerMovieTopRate.adapter = MovieAdapterRecycler(activity as MainActivity)

        movieService.tryGetPopularMovies()
        movieService.tryGetTopRatedMovies()
        movieService.tryGetUpcomingMovies()

        isRunning = true
        viewPagerThread = Thread {
            val totalSize = (binding.viewPager.adapter as MoviePagerAdapter).itemCount
            while(true) {
                // ?????? ??? while(isRunning)?????? ?????? ????????????, while(isRunning)?????? ?????? ???,
                // onDestroyView()?????? inRunning ??? false ??? ????????? ???????????? ?????? handler.post ??? main Thread ???
                // ????????? ???????????? ???????????? ?????????, super.onDestroyView ?????? main Thread ?????? handler.post ??? ?????? ?????????
                // ???????????? ?????? ??? binding ??? ????????? ??????????????? (super.onDestroyView ?????? _binding = null ??? ?????? ??????)
                Thread.sleep(2000)
                if (isRunning){
                    handler.post {
                        binding.viewPager.currentItem = (binding.viewPager.currentItem + 1) % totalSize
                    }
                } else {
                    break
                }
            }
        }
    }

    override fun onDestroyView() {
        isRunning = false
        viewPagerThread.join()
        super.onDestroyView()
    }

    override fun onGetPopularMovieSuccess(movieList: List<MovieMeta>) {
        (binding.recyclerMoviePopular.adapter as MovieAdapterRecycler).addMovieData(movieList)
    }

    override fun onGetTopRatedMovieSuccess(movieList: List<MovieMeta>) {
        (binding.recyclerMovieTopRate.adapter as MovieAdapterRecycler).addMovieData(movieList)
    }

    override fun onGetUpcomingMovieSuccess(movieList: List<MovieMeta>) {
        (binding.viewPager.adapter as MoviePagerAdapter).addMovieData(movieList)
        binding.tvViewpagerCount.text = getString(R.string.movie_viewpager_count, 1, movieList.size)
        viewPagerThread.start()
    }

    override fun onGetMovieFailure(message: String) {
        (activity as MainActivity).showCustomToast("onGetMovieFailure")
    }
}