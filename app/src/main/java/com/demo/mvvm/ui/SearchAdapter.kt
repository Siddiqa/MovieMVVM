package com.demo.mvvm.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.mvvm.R
import com.demo.mvvm.databinding.RowEmptyBinding
import com.demo.mvvm.databinding.RowSearchBinding
import com.demo.mvvm.model.Search
import com.demo.mvvm.utils.MovieItemCallback

class SearchAdapter(
    private val items: List<Search>?,
    private val context: Context,
    private val movieItemCallback: MovieItemCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder = when (viewType) {

            VIEW_TYPE_EMPTY -> {
                val emptyBinding = DataBindingUtil.inflate<RowEmptyBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.row_empty,
                    parent,
                    false
                )
                return EmptyHolder(emptyBinding)
            }
            VIEW_TYPE_NORMAL -> {
                val searchBinding = DataBindingUtil.inflate<RowSearchBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.row_search, parent, false
                )
                searchBinding.callback = movieItemCallback
                return SeachHolder(searchBinding)
            }
            else->throw IllegalArgumentException()

        }
        return viewHolder

    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val itemType = getItemViewType(position)
        if (itemType == 1) {
            (viewHolder as SeachHolder).Bind(viewHolder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!items!!.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }


    override fun getItemCount(): Int {
        return items?.size ?: 1
    }

    inner class SeachHolder(var rowSearchBinding: RowSearchBinding) :
        RecyclerView.ViewHolder(rowSearchBinding.root) {
        fun Bind(holder: SeachHolder, position: Int) {
            holder.rowSearchBinding.movie = items!![position]
            holder.rowSearchBinding.executePendingBindings()
        }

    }

    inner class EmptyHolder(var emptyBinding: RowEmptyBinding) :
        RecyclerView.ViewHolder(emptyBinding.root)

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
    }

}