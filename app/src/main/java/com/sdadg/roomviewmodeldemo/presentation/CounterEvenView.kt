package com.sdadg.roomviewmodeldemo.presentation

import android.arch.lifecycle.LifecycleOwner
import android.content.Context
import android.util.AttributeSet

class CounterEvenView: CounterView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun setPostId(postId: Long) {
        if (activityContext is LifecycleOwner) {
//            val comments = repository.getOnlyEventCommentsByPostId(postId)

//            comments.observe(activityContext as LifecycleOwner, observer)
        }
    }
}