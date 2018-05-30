package com.sdadg.roomviewmodeldemo.data.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.sdadg.roomviewmodeldemo.R
import com.sdadg.roomviewmodeldemo.data.entities.Post
import com.sdadg.roomviewmodeldemo.data.viewholders.PostViewHolder
import com.sdadg.roomviewmodeldemo.utilities.StringUtilities

class PostRecyclerViewAdapter(private val listener: PostRecyclerViewAdapter.Listeners): RecyclerView.Adapter<PostViewHolder>() {

    private var data: List<Post> = arrayListOf()

    fun loadData(d: List<Post>){
        data = d
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(View.inflate(parent.context, R.layout.post_item, null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.container.setOnClickListener({
            listener.onItemClick(data[position].postId, data[position].title)
        })

        holder.tvTitle.text = data[position].title
        holder.tvPostedAt.text = StringUtilities.formatTimestamp(data[position].postedAt)
    }

    interface Listeners {
        fun onItemClick(postId: Long, postTitle: String)
    }
}