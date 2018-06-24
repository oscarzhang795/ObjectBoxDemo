package com.sdadg.roomviewmodeldemo.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.sdadg.roomviewmodeldemo.data.daos.CommentDao
import com.sdadg.roomviewmodeldemo.data.daos.PostDao
import com.sdadg.roomviewmodeldemo.data.entities.Comment
import com.sdadg.roomviewmodeldemo.data.entities.Post
import com.sdadg.roomviewmodeldemo.data.migrations.Migration1To2

@Database (entities = [Post::class, Comment::class], version = 2)
abstract class DemoRoomDatabaseAbstract: RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao

    companion object {
        var mInstance: DemoRoomDatabaseAbstract? = null

        @JvmField
        val MIGRATION_1_2 = Migration1To2()

        fun getInstance(context: Context): DemoRoomDatabaseAbstract? {
            if (mInstance == null) {

                synchronized(DemoRoomDatabaseAbstract::class) {
                    mInstance = Room.databaseBuilder(context.applicationContext, DemoRoomDatabaseAbstract::class.java, "RoomDemo.db")
                            //.addMigrations(MIGRATION_1_2)
                            .fallbackToDestructiveMigration()  //Since we don't care about migrating right now.
                            .build()


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