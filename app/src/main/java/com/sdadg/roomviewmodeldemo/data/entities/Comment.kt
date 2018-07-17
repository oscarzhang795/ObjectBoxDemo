package com.sdadg.roomviewmodeldemo.data.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Comment (
        @Id
        var commentId: Long?,

        var postId: Long,

        var comment: String,

        var commentedAt: Long
)