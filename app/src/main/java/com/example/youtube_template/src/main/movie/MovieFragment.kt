package com.example.youtube_template.src.main.movie

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.youtube_template.R
import com.example.youtube_template.config.BaseFragment
import com.example.youtube_template.databinding.FragmentMovieBinding
import com.example.youtube_template.src.main.MainActivity
import com.example.youtube_template.src.main.movie.adapter.MoviePagerAdapter
import com.example.youtube_template.src.main.movie.models.MovieMeta


class MovieFragment : BaseFragment<FragmentMovieBinding>(FragmentMovieBinding::bind, R.layout.fragment_movie), MovieFragmentView{

    private val movieService = MovieService(this)
    private var isRunning = false
    private lateinit var viewPagerThread : Thread
    private val handler = Handler(Looper.getMainLooper())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setSupportActionBar(binding.movieAppbar)
        (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.viewPager.adapter = MoviePagerAdapter(activity as MainActivity)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvViewpagerCount.text = getString(R.string.movie_viewpager_count, position + 1, (binding.viewPager.adapter as MoviePagerAdapter).itemCount)
            }
        })
        movieService.tryGetPopularMovies()

        isRunning = true
        viewPagerThread = Thread {
            val totalSize = (binding.viewPager.adapter as MoviePagerAdapter).itemCount
            while(true) {
                // 이걸 왜 while(isRunning)으로 하지 않았냐면, while(isRunning)으로 했을 시,
                // onDestroyView()에서 inRunning 이 false 가 되었다 하더라도 이미 handler.post 로 main Thread 에
                // 요청을 보내놓은 상태이기 때문에, super.onDestroyView 이후 main Thread 에서 handler.post 로 보낸 작업을
                // 수행하려 했을 때 binding 에 접근이 불가능하다 (super.onDestroyView 에서 _binding = null 이 되기 때문)
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

    override fun onGetPopularMovieSuccess(movieList: List<MovieMeta>) {
        (binding.viewPager.adapter as MoviePagerAdapter).addMovieData(movieList)
        binding.tvViewpagerCount.text = getString(R.string.movie_viewpager_count, 1, movieList.size)
        viewPagerThread.start()
    }

    override fun onDestroyView() {
        isRunning = false
        viewPagerThread.join()
        super.onDestroyView()
    }

    override fun onGetTopRatedMovieSuccess(movieList: List<MovieMeta>) {

    }

    override fun onGetUpcomingMovieSuccess(movieList: List<MovieMeta>) {

    }

    override fun onGetMovieFailure(message: String) {

    }
}