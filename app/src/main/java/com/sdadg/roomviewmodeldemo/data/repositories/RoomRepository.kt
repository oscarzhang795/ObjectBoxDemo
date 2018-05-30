package com.sdadg.roomviewmodeldemo.data.repositories

import android.content.Context
import com.sdadg.roomviewmodeldemo.data.database.DemoRoomDatabaseAbstract
import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.data.entities.Post

class RoomRepository(var context: Context): IDataRepository {

    override fun getAllPosts(): List<Post> {
        return DemoRoomDatabaseAbstract.getInstance(context)?.postDao()?.getAllPosts()?: arrayListOf()
    }

    override fun getPostById(id: Long): Post? {
        return DemoRoomDatabaseAbstract.getInstance(context)?.postDao()?.getPostByPostId(id)
    }

    override fun insertPost(post: Post): Long {
        return DemoRoomDatabaseAbstract.getInstance(context)?.postDao()?.insertPost(post)?: -1
    }

    override fun deletePost(post: Post) {
        DemoRoomDatabaseAbstract.getInstance(context)?.postDao()?.deletePost(post)
    }

    override fun deletePosts() {
        DemoRoomDatabaseAbstract.getInstance(context)?.postDao()?.deletePosts()
    }

    override fun updatePost(post: Post){
        DemoRoomDatabaseAbstract.getInstance(context)?.postDao()?.updatePost(post)
    }

    override fun getAllCommentsByPostId(id: Long): List<Comment> {
        return DemoRoomDatabaseAbstract.getInstance(context)?.commentDao()?.getCommentsByPostId(id)?: arrayListOf()
    }

    override fun insertComment(comment: Comment): Long {
        return DemoRoomDatabaseAbstract.getInstance(context)?.commentDao()?.insertComment(comment)?: -1
    }

    override fun deleteComment(comment: Comment) {
        DemoRoomDatabaseAbstract.getInstance(context)?.commentDao()?.deleteComment(comment)
    }

    override fun updateComment(comment: Comment) {
        DemoRoomDatabaseAbstract.getInstance(context)?.commentDao()?.updateComment(comment)
    }
}