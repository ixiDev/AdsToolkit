package com.ixidev.adstoolkit.core

/**
 * Created by ABDELMAJID ID ALI on 3/9/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
interface FullScreenAdsListener {
    fun onAdDismissed(isShowed: Boolean = true)
    fun onShowAdFailed(error: Exception)
    fun onAdShowed()
}