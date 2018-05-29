package com.sdadg.roomviewmodeldemo.data.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.sdadg.roomviewmodeldemo.R
import com.sdadg.roomviewmodeldemo.data.viewholders.PostViewHolder
import com.sdadg.roomviewmodeldemo.presentation.roomviewmodeldemo.data.entities.Post

class PostRecyclerViewAdapter(val listener: PostRecyclerViewAdapter.Listeners): RecyclerView.Adapter<PostViewHolder>() {

    private lateinit var data: List<Post>

    fun loadData(d: List<Post>){
        data = d;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(View.inflate(parent.context, R.layout.post_item, null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.container.setOnClickListener({
            listener.onItemClick(data.get(position).postId)
        })

        holder.tvTitle.text = data.get(position).title
        holder.tvPostedAt.text = data.get(position).postedAt.toString()
    }

    interface Listeners {
        fun onItemClick(postId: Long)
    }
}