package com.sdadg.roomviewmodeldemo.presentation

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.sdadg.roomviewmodeldemo.R
import com.sdadg.roomviewmodeldemo.data.entities.Comment
open class CounterView: TextView {

    lateinit var activityContext: Context
//    lateinit var repository: IDataRepository
    lateinit var message: String

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

        val attributes = context?.obtainStyledAttributes(attrs, R.styleable.CounterView, 0, 0)

        try {


            message = attributes?.getString(R.styleable.CounterView_message) ?: ""
        }
        finally {
            attributes?.recycle()
        }

    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    init {
        if (context != null) {
            activityContext = context
//            repository = RoomRepository(activityContext)
        }
    }

    open fun setPostId(postId: Long) {
        if (activityContext is LifecycleOwner) {
//            val comments = repository.getAllCommentsByPostId(postId)

//            comments.observe(activityContext as LifecycleOwner, observer)
        }
    }

    open val observer: Observer<List<Comment>> = Observer { data ->
        if (data != null) {
            setText(message + data.size)
        }
    }
}