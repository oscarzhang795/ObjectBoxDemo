package com.sdadg.roomviewmodeldemo.data.daos

import android.arch.persistence.room.*
import com.sdadg.roomviewmodeldemo.data.entities.Comment

@Dao
interface CommentDao {

    @Query("Select * from Comment where postId = :postId")
    fun getCommentsByPostId(postId: Long): List<Comment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: Comment): Long

    @Delete
    fun deleteComment(comment: Comment)

    @Update
    fun updateComment(comment: Comment)
}