package com.ixidev.adstoolkit.core

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresPermission

interface INativeAd {
    /**
     * Load and init Native ad
     * @param context : activity context
     * @param adId : unit ad ID
     */
    @RequiresPermission("android.permission.INTERNET")
    fun load(context: Context, adId: String)

    /**
     *  show the add on [container]
     *  @param container: Layout View to inflate the ad
     */
    fun render(container: FrameLayout)

    /**
     * Destroy the add
     */
    fun destroy()

}