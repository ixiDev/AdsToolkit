package com.ixidev.adstoolkit.admob

import android.content.Context
import android.widget.FrameLayout
import androidx.annotation.RequiresPermission
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.ixidev.adstoolkit.core.IBannerAd

@Suppress("unused")
class SimpleAdMobBanner(private val adRequest: AdRequest.Builder) : IBannerAd {

    private var _bannerAd: AdView? = null
    private val bannerAd: AdView
        get() = _bannerAd!!


    @RequiresPermission("android.permission.INTERNET")
    override fun load(context: Context, adId: String) {
        if (_bannerAd == null) {
            _bannerAd = AdView(context).apply {
                adUnitId = adId
                adSize = AdSize.BANNER
            }
        }

        bannerAd.loadAd(adRequest.build())
    }

    override fun render(container: FrameLayout) {
        container.removeAllViews()
        container.addView(bannerAd)
        container.isVisible = false
        bannerAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                container.isVisible = true
            }
        }
    }

    override fun destroy() {
        _bannerAd?.adListener = null
        _bannerAd?.removeAllViews()
        _bannerAd?.destroy()
        _bannerAd = null
    }

    override fun onPause() {
        _bannerAd?.pause()
    }

    override fun onResume() {
        _bannerAd?.resume()
    }
}