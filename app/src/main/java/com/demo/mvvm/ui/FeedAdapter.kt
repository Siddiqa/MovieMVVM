package com.demo.mvvm.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.mvvm.R
import com.demo.mvvm.databinding.RowMovieBinding
import com.demo.mvvm.model.Search
import com.demo.mvvm.utils.MovieItemCallback


class FeedAdapter(
    var context: Context,
    var callback: MovieItemCallback,
    var movieList: ArrayList<Search>
) :
    RecyclerView.Adapter<FeedAdapter.MovieHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieHolder {
        val binding: RowMovieBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(parent.context), R.layout.row_movie,
                parent, false
            )

        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.binding.callback = callback
        holder.binding.movie = movieList[position]
        holder.binding.executePendingBindings()
    }


    override fun getItemCount(): Int {
        return if (movieList == null) {
            0
        } else movieList.size
    }

    class MovieHolder(binding: RowMovieBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        val binding: RowMovieBinding = binding

    }


}