package com.sdadg.roomviewmodeldemo.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.sdadg.roomviewmodeldemo.presentation.roomviewmodeldemo.data.entities.Post

@Entity (foreignKeys = arrayOf(ForeignKey(entity = Post::class,
                                            parentColumns = arrayOf("postId"),
                                            childColumns = arrayOf("postId"),
                                            onDelete = ForeignKey.CASCADE)))
data class Comment (

        @PrimaryKey(autoGenerate = true)
        val commentId: Long?,

        @ColumnInfo(name = "postId")
        val postId: Long,

        @ColumnInfo(name = "comment")
        val comment: String,

        @ColumnInfo(name = "commentedAt")
        val commentedAt: Long
) {
}