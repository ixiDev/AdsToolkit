package com.ixidev.adstoolkit.admob

import android.content.Context
import android.widget.FrameLayout
import androidx.annotation.RequiresPermission
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.ixidev.adstoolkit.core.AdsToolkit
import com.ixidev.adstoolkit.core.INativeAd

class SimpleAdMobNative(
    private val adRequest: AdRequest.Builder,
    private val container: FrameLayout
) : INativeAd {

    private var adLoader: AdLoader? = null
    private var nativeAd: UnifiedNativeAd? = null
    private var isAdLoaded = false

    @RequiresPermission("android.permission.INTERNET")
    override fun load(context: Context, adId: String) {
        adLoader = AdLoader.Builder(context, adId)
            .forUnifiedNativeAd { ad ->
                if (adLoader?.isLoading == true) {
                    nativeAd = null
                } else {
                    nativeAd = ad
                    isAdLoaded = true
                    render(container)
                }

            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    AdsToolkit.logD("onAdFailedToLoad: ${adError.message}")
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .build()
            ).build()
        adLoader?.loadAd(adRequest.build())
    }

    override fun render(container: FrameLayout) {

        if (!isAdLoaded())
            return
        val adTemplate: TemplateView = TemplateView(container.context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
        }
        container.addView(adTemplate)
        val styles = NativeTemplateStyle
            .Builder()
            // .withMainBackgroundColor()
            .build()
        adTemplate.setStyles(styles)
        adTemplate.setNativeAd(nativeAd)

    }

    override fun destroy() {
        nativeAd?.destroy()
        adLoader = null
        nativeAd = null
    }

    private fun isAdLoaded(): Boolean = isAdLoaded && nativeAd != null
}