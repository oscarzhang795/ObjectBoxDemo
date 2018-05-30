package com.sdadg.roomviewmodeldemo.data.viewholders

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.sdadg.roomviewmodeldemo.R

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var container: ConstraintLayout = itemView.findViewById(R.id.container)
    var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    var tvPostedAt: TextView = itemView.findViewById(R.id.tvPostedAt)
}
