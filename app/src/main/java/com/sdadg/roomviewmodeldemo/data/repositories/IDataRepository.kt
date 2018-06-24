package com.sdadg.roomviewmodeldemo.data.repositories

import android.arch.lifecycle.LiveData
import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.data.entities.Post

interface IDataRepository {

    fun getAllPosts(): LiveData<List<Post>>
    fun getPostById(id: Long): LiveData<Post>?
    fun insertPost(post: Post): Long
    fun deletePost(post: Post)
    fun updatePost(post: Post)
    fun deletePosts()

    fun getAllCommentsByPostId(id: Long): LiveData<List<Comment>>
    fun getOnlyEventCommentsByPostId(id: Long): LiveData<List<Comment>>
    fun insertComment(comment: Comment): Long
    fun deleteComment(comment: Comment)
    fun updateComment(comment: Comment)
}