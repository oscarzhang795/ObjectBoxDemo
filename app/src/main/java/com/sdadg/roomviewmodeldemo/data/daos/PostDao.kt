package com.sdadg.roomviewmodeldemo.data.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.sdadg.roomviewmodeldemo.data.entities.Post

@Dao
interface PostDao {

    @Query("Select * from post order by postedAt desc")
    fun getAllPosts(): LiveData<List<Post>>

    @Query("Select * from post where postId = :postId")
    fun getPostByPostId(postId: Long): LiveData<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post): Long

    @Delete
    fun deletePost(post: Post)

    @Update
    fun updatePost(post: Post)

    @Query("Delete from post")
    fun deletePosts()
}

