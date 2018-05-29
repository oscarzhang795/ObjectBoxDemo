package com.sdadg.roomviewmodeldemo.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.sdadg.roomviewmodeldemo.data.daos.CommentDao
import com.sdadg.roomviewmodeldemo.data.daos.PostDao
import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.presentation.roomviewmodeldemo.data.entities.Post

@Database(entities = arrayOf(Post::class, Comment::class), version = 1)
abstract class DemoRoomDatabaseAbstract: RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao

    companion object {
        var mInstance: DemoRoomDatabaseAbstract? = null

        fun getInstance(context: Context): DemoRoomDatabaseAbstract? {
            if (mInstance == null) {

                synchronized(DemoRoomDatabaseAbstract::class) {
                    mInstance = Room.databaseBuilder(context.applicationContext, DemoRoomDatabaseAbstract::class.java, "RoomDemo.db").allowMainThreadQueries().build()
                }
            }

            return mInstance
        }
    }

    fun destroyInstance() {
        mInstance?.close()
        mInstance = null
    }
}