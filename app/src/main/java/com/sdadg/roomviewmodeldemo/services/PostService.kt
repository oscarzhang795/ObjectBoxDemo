package com.sdadg.roomviewmodeldemo.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sdadg.roomviewmodeldemo.ObjectBox
import com.sdadg.roomviewmodeldemo.data.entities.Post
import com.sdadg.roomviewmodeldemo.utilities.StringUtilities
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class PostService : Service() {
    lateinit var mPostBox: Box<Post>
    lateinit var scheduledThreadPoolExecutor: ScheduledExecutorService
    val postName = "Automatically created Post #:"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            super.onStartCommand(intent, flags, startId)

        mPostBox = (application as ObjectBox).boxStore.boxFor()

        startAutomaticPostCreation()
        return Service.START_NOT_STICKY
    }

    fun startAutomaticPostCreation() {
        scheduledThreadPoolExecutor = Executors.newScheduledThreadPool(2)

        val addPostRunnable = Runnable {
            mPostBox.put(StringUtilities.createRandomPost(postName))
        }

        scheduledThreadPoolExecutor.scheduleAtFixedRate(addPostRunnable, 5, 5, TimeUnit.SECONDS)


    }

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        scheduledThreadPoolExecutor?.shutdown()
        super.onDestroy()
    }
}
