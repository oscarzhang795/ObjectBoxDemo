package com.sdadg.roomviewmodeldemo.data.viewholders

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.sdadg.roomviewmodeldemo.R

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var container: ConstraintLayout
    var tvTitle: TextView
    var tvPostedAt: TextView

    init {
        container = itemView.findViewById(R.id.container)
        tvTitle = itemView.findViewById(R.id.tvTitle)
        tvPostedAt = itemView.findViewById(R.id.tvPostedAt)
    }
}
