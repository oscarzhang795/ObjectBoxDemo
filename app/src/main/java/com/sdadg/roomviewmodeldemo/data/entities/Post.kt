package com.sdadg.roomviewmodeldemo.data.entities

import android.graphics.Color
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Post (
        @Id
        var postId: Long?,

        var title: String,

        var postedAt: Long,

        var textColor: Int = Color.BLACK
)