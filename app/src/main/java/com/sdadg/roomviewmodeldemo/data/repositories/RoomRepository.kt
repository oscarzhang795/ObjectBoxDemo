package com.sdadg.roomviewmodeldemo.data.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import com.sdadg.roomviewmodeldemo.data.database.DemoRoomDatabaseAbstract
import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.data.entities.Post
import com.sdadg.roomviewmodeldemo.utilities.StringUtilities

class RoomRepository(var context: Context): IDataRepository {

    override fun getAllPosts(): LiveData<List<Post>> {
        return DemoRoomDatabaseAbstract.getInstance(context)?.postDao()?.getAllPosts()?: MutableLiveData<List<Post>>()
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

    override fun getAllCommentsByPostId(id: Long): LiveData<List<Comment>> {
        return DemoRoomDatabaseAbstract.getInstance(context)?.commentDao()?.getCommentsByPostId(id)?: MutableLiveData<List<Comment>>()
    }

    override fun getOnlyEventCommentsByPostId(id: Long): LiveData<List<Comment>> {
        val allComments: LiveData<List<Comment>> = DemoRoomDatabaseAbstract.getInstance(context)?.commentDao()?.getCommentsByPostId(id)?: MutableLiveData<List<Comment>>()

        return Transformations.map(allComments, { data -> filterOnlyEvenIds(data) })
    }

    fun filterOnlyEvenIds(allComments: List<Comment>): List<Comment> {
        val filteredComments = allComments.filter (fun(comment : Comment) : Boolean {
            /*Log.d("RoomRepository", "commentId = ${comment.commentId} is " + ((comment.commentId ?: 0 % 2) == 0L))*/

            return ((((comment.commentId ?: 0) -1) % 2) == 0L)
        })

        return filteredComments
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