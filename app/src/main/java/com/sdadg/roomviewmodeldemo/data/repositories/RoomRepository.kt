package com.sdadg.roomviewmodeldemo.data.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import com.sdadg.roomviewmodeldemo.data.database.DemoRoomDatabaseAbstract
import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.data.entities.Post
import com.sdadg.roomviewmodeldemo.utilities.StringUtilities

class RoomRepository(var context: Context): IDataRepository {

    override fun getAllPosts(): LiveData<List<Post>> {
        var oldList = DemoRoomDatabaseAbstract.getInstance(context)?.postDao()?.getAllPosts()?: MutableLiveData<List<Post>>()

        /*var newList = MutableLiveData<List<Post>>()

        //https://android.jlelse.eu/exploring-livedata-architecture-component-f9375d3644ee
        Transformations.map(oldList) {
            data -> {newList = addColor(data)}
        }

        return newList*/

        return oldList
    }

    override fun getPostById(id: Long): LiveData<Post>? {
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

    private fun addColor(posts: MutableLiveData<List<Post>>): MutableLiveData<List<Post>> {

        for (post in posts.value!!) {
            post.textColor = StringUtilities.getRandomColor()
        }

        return posts
    }
}