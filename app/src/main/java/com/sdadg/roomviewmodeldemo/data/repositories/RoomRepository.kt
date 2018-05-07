package com.sdadg.roomviewmodeldemo.data.repositories

import android.content.Context
import com.sdadg.roomviewmodeldemo.data.database.DemoRoomDatabaseAbstract
import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.presentation.roomviewmodeldemo.data.entities.Post

class RoomRepository(var context: Context): IDataRepository {

    override fun getAllPosts(): List<Post> {
        var results: List<Post>

        val postDao = DemoRoomDatabaseAbstract.getInstance(context).postDao()

        results = postDao.getAllPosts()

        return results
    }

    override fun getPostById(id: Long): Post {
        val postDao = DemoRoomDatabaseAbstract.getInstance(context).postDao()

        return postDao.getPostByPostId(id)
    }

    override fun insertPost(post: Post): Long {
        return DemoRoomDatabaseAbstract.getInstance(context).postDao().insertPost(post)
    }

    override fun deletePost(post: Post) {
        DemoRoomDatabaseAbstract.getInstance(context).postDao().deletePost(post)
    }

    override fun updatePost(post: Post){
        DemoRoomDatabaseAbstract.getInstance(context).postDao().updatePost(post)
    }

    override fun getAllCommentsByPostId(id: Long): List<Comment> {
        return DemoRoomDatabaseAbstract.getInstance(context).commentDao().getCommentsByPostId(id)
    }

    override fun insertComment(comment: Comment): Long {
        return DemoRoomDatabaseAbstract.getInstance(context).commentDao().insertComment(comment)
    }

    override fun deleteComment(comment: Comment) {
        DemoRoomDatabaseAbstract.getInstance(context).commentDao().deleteComment(comment)
    }

    override fun updateComment(comment: Comment) {
        DemoRoomDatabaseAbstract.getInstance(context).commentDao().updateComment(comment)
    }
}