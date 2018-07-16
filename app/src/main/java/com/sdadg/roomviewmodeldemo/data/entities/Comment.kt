package com.sdadg.roomviewmodeldemo.data.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Comment (
        @Id val commentId: Long?,

        val postId: Long,

        val comment: String,

        val commentedAt: Long
)