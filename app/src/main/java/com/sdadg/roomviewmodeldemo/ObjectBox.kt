package com.sdadg.roomviewmodeldemo

import android.app.Application
import com.sdadg.roomviewmodeldemo.data.entities.MyObjectBox
import io.objectbox.BoxStore

class ObjectBox : Application() {

    lateinit var boxStore: BoxStore

    override fun onCreate() {
        super.onCreate()
        boxStore = MyObjectBox.builder().androidContext(this).build()
    }
}