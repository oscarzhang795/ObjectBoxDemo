package com.sdadg.roomviewmodeldemo.presentation.roomviewmodeldemo.data.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Post (
        @Id val postId: Long,

        val title: String,

        val postedAt: Long
)