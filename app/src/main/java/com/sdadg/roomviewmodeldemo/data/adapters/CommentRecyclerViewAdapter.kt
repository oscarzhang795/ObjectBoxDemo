package com.sdadg.roomviewmodeldemo.data.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.sdadg.roomviewmodeldemo.R
import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.data.viewholders.CommentViewHolder

class CommentRecyclerViewAdapter(val listener: CommentRecyclerViewAdapter.Listeners): RecyclerView.Adapter<CommentViewHolder>() {

    private lateinit var data: List<Comment>

    fun loadData(d: List<Comment>){
        data = d;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(View.inflate(parent.context, R.layout.post_item, null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getItemByPosition(position: Int): Comment {
        return data.get(position)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.container.setOnClickListener({
            listener.onItemClick(data.get(position).commentId?: -1)
            listener.onItemClickByPosition(position)
        })

        holder.tvTitle.text = data.get(position).comment
        holder.tvPostedAt.text = data.get(position).commentedAt.toString()
    }

    interface Listeners {
        fun onItemClick(commentId: Long)
        fun onItemClickByPosition(position: Int)
    }
}