package com.ixidev.adstoolkit.core

import android.util.Log

/**
 * Created by ABDELMAJID ID ALI on 1/23/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
object AdsToolkit {

    private const val TAG = "AdsToolkit"
    private var log_enabled = false
    fun init(debug: Boolean) {
        log_enabled = debug
    }

    fun logD(message: String) {
        if (log_enabled)
            Log.d(TAG, ": $message")
    }

    fun logE(message: String,error: Exception?=null) {
        if (log_enabled) {
            Log.e(TAG, "$message: ", error)
        }
    }
}