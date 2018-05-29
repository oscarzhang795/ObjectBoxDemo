package com.sdadg.roomviewmodeldemo.data.entities

data class Comment (
        val commentId: Long?,

        val postId: Long,

        val comment: String,

        val commentedAt: Long
) {
}