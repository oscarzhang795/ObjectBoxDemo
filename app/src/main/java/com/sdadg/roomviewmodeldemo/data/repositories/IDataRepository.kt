package com.sdadg.roomviewmodeldemo.data.repositories

import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.presentation.roomviewmodeldemo.data.entities.Post

interface IDataRepository {

    fun getAllPosts(): List<Post>
    fun getPostById(id: Long): Post?
    fun insertPost(post: Post): Long
    fun deletePost(post: Post)
    fun updatePost(post: Post)
    fun deletePosts()

    fun getAllCommentsByPostId(id: Long): List<Comment>
    fun insertComment(comment: Comment): Long
    fun deleteComment(comment: Comment)
    fun updateComment(comment: Comment)
}