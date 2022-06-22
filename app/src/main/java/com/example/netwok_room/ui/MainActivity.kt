package com.example.netwok_room.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netwok_room.adapter.MovieAdapter
import com.example.netwok_room.adapter.PaginationScrollListener
import com.example.netwok_room.databinding.ActivityMainBinding

import com.example.netwok_room.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.internal.notify


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private  val viewModel: MainViewModel by viewModels()
    private lateinit var  binding : ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private var page = 1
    private var positionAdapter = 0

    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieAdapter = MovieAdapter()
        val linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.movieRecyclerview.layoutManager = linearLayoutManager
        binding.movieRecyclerview.adapter = movieAdapter
        lifecycleScope.launch() {
            viewModel.deleteall()
        }
        lifecycleScope.launch() {
            viewModel.deleteallpage()
        }


        lifecycleScope.launch() {
            viewModel.getGenresLocal.collect{
                if (!it.isNullOrEmpty()){
                    movieAdapter.removeLoading()
                    movieAdapter.setList(it,positionAdapter,this@MainActivity)

                }else{
                    loadData()
                }
            }

        }

        lifecycleScope.launch() {
            viewModel.getLastPage.collect{
                it?.let {
                    lastPage -> page=lastPage .lastpage
                }
                Log.d("--> GET","page from db $page")
            }
        }

        binding.movieRecyclerview.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                movieAdapter.setLoading()
                //you have to call loadmore items to get more data
                page++

                loadData()
            }
        })

    }


    private fun loadData(){
        viewModel.getGenres(page).observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                   // binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()){
                        isLoading = false
                    }
                }
                Resource.Status.ERROR ->
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->{
                   // binding.progressBar.visibility = View.VISIBLE
                }

            }
        })

    }
}