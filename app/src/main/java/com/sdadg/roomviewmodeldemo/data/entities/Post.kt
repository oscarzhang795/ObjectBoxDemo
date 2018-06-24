package com.sdadg.roomviewmodeldemo.data.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.graphics.Color

@Entity
data class Post (

        @PrimaryKey(autoGenerate = true)
        val postId: Long?,

        @ColumnInfo (name = "title")
        val title: String,

        @ColumnInfo(name = "postedAt")
        val postedAt: Long,

        @ColumnInfo(name = "textColor")
        var textColor: Int = Color.BLACK
)