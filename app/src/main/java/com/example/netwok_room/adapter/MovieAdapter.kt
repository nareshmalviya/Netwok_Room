package com.example.netwok_room.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.netwok_room.databinding.ItemLoadingBinding

import com.example.netwok_room.databinding.MovieItemBinding
import com.example.netwok_room.model.GenresMovie
import com.example.netwok_room.utils.Constants

class MovieAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var LOADING = false
    private lateinit var activity:Context;
    private  val  movieList  by lazy {
        ArrayList<GenresMovie>()
    }

    fun setLoading(){
        LOADING = true
        movieList.add(GenresMovie(1,1,"","",""))
        notifyItemInserted(movieList.size-1)
    }

    fun removeLoading() {
        if (movieList.size==0) return
        LOADING = false
        val position: Int = movieList.size - 1
        val item: GenresMovie = movieList.get(position)
        if (item != null) {
            movieList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

     fun setList(list: List<GenresMovie>,position: Int, activityX: Context) {
         LOADING=false
         movieList.clear()
         movieList.addAll(list)
         activity =activityX
         notifyItemInserted(movieList.size)
    }

    inner class MovieHolder(val movieItemBinding: MovieItemBinding) :RecyclerView.ViewHolder(movieItemBinding.root){

        fun bind(data: GenresMovie) {
            movieItemBinding.header.text = data.original_title
            movieItemBinding.dertail.text = data.overview
            Glide.with(activity).load("${Constants.IMAGEPATH}${data.poster_path}").into( movieItemBinding.poster)

        }
    }


    inner class LoadingHolder(val itemLoadingBinding: ItemLoadingBinding) :RecyclerView.ViewHolder(itemLoadingBinding.root){

        fun bind() {
            itemLoadingBinding.progressBar.visibility = View.VISIBLE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_LOADING){
            val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return  LoadingHolder(binding )

        }

        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  MovieHolder(binding )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieHolder){
            holder.bind(movieList.get(position))
        }else  if (holder is LoadingHolder){
            holder.bind()
        }


        }

    override fun getItemViewType(position: Int): Int {
         if (LOADING){
             return if (position == movieList.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
        }  else return VIEW_TYPE_ITEM

    }

    override fun getItemCount(): Int {
       return movieList.size
    }
}