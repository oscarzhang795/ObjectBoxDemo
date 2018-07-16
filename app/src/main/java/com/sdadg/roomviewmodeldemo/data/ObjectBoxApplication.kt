package com.sdadg.roomviewmodeldemo.data

import android.app.Application
import com.sdadg.roomviewmodeldemo.MyObjectBox
import io.objectbox.BoxStore

class ObjectBoxApplication : Application() {

    //Must build with object box entity before this generated class appears
    lateinit var boxStore: BoxStore

    override fun onCreate() {
        super.onCreate()
        boxStore = MyObjectBox.builder().androidContext(this).build()
    }
}