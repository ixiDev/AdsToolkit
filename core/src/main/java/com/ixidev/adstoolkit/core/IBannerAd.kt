package com.ixidev.adstoolkit.core

import android.content.Context
import android.widget.FrameLayout
import androidx.annotation.RequiresPermission

/**
 * Created by ABDELMAJID ID ALI on 25/10/2020.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */

interface IBannerAd {

    /**
     * Load and init banner ad
     * @param context : activity context
     * @param adId : banner unit ad ID
     */
    @RequiresPermission("android.permission.INTERNET")
    fun load(context: Context, adId: String)

    /**
     *  show banner on [container]
     *  @param container: Layout View to inflate banner ad
     */
    fun render(container: FrameLayout)

    /**
     * call this on activity destroyed
     */
    fun destroy()

    /**
     * call this on activity paused
     */
    fun onPause()

    /**
     * call this on activity resumed
     */
    fun onResume()
}