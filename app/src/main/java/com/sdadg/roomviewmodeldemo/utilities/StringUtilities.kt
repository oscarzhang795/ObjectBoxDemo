package com.sdadg.roomviewmodeldemo.utilities

import java.text.SimpleDateFormat
import java.util.*

class StringUtilities {
    companion object {
         fun formatTimestamp(timestamp: Long): String {

            val formatter = SimpleDateFormat("MM/dd/YYYY hh:mm:ss", Locale.US)
            return formatter.format(timestamp)
        }
    }
}