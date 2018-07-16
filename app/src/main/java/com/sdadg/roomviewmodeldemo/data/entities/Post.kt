package com.sdadg.roomviewmodeldemo.presentation.roomviewmodeldemo.data.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Post (
        @Id var postId: Long,

        var title: String,

        var postedAt: Long
)