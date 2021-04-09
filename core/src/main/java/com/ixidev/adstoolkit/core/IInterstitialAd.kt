package com.ixidev.adstoolkit.core

import android.app.Activity
import android.content.Context
import androidx.annotation.RequiresPermission

/**
 * Created by ABDELMAJID ID ALI on 25/10/2020.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */

typealias OnInterstitialClosed = () -> Unit

interface IInterstitialAd {

    var listener: FullScreenAdsListener?

    /**
     * Load and init banner ad
     * @param context : activity context
     * @param adId : banner unit ad ID
     */
    @RequiresPermission("android.permission.INTERNET")
    fun load(context: Context, adId: String)
    fun isLoaded(): Boolean

    fun show(activity: Activity, listener: FullScreenAdsListener)
    fun destroy()
}