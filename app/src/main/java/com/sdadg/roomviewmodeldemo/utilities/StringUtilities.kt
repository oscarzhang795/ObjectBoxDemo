package com.sdadg.roomviewmodeldemo.utilities

import android.graphics.Color
import com.sdadg.roomviewmodeldemo.data.entities.Post
import java.text.SimpleDateFormat
import java.util.*

class StringUtilities {
    companion object {
         fun formatTimestamp(timestamp: Long): String {

            val formatter = SimpleDateFormat("MM/dd/YYYY hh:mm:ss", Locale.US)
            return formatter.format(timestamp)
        }

        fun getRandomColor(): Int {
            val random = Random()
            return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
        }

        fun createRandomPost(name: String): Post {
            val random = Random()
            val tempInt = random.nextInt()
            val post = Post(null, "$name $tempInt", Calendar.getInstance().timeInMillis, getRandomColor())

            return post
        }
    }
}