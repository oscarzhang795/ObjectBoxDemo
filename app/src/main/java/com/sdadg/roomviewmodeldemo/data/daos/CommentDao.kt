package com.sdadg.roomviewmodeldemo.data.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.sdadg.roomviewmodeldemo.data.entities.Comment

@Dao
interface CommentDao {

    @Query("Select * from Comment where postId = :postId order by commentedAt desc")
    fun getCommentsByPostId(postId: Long): LiveData<List<Comment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: Comment): Long

    @Delete
    fun deleteComment(comment: Comment)

    @Update
    fun updateComment(comment: Comment)
}