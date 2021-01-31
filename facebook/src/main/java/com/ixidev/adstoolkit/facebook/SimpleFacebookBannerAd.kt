package com.ixidev.adstoolkit.facebook

import android.content.Context
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.facebook.ads.*
import com.ixidev.adstoolkit.core.AdsToolkit
import com.ixidev.adstoolkit.core.IBannerAd

/**
 * Created by ABDELMAJID ID ALI on 1/23/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
class SimpleFacebookBannerAd : IBannerAd {

    private var _bannerAd: AdView? = null
    private val bannerAd: AdView
        get() = _bannerAd!!


    override fun load(context: Context, adId: String) {
        if (_bannerAd == null) {
            _bannerAd = AdView(
                context,
                adId,
                AdSize.BANNER_HEIGHT_50
            )
        }
        bannerAd.isVisible = false
        val config = bannerAd.buildLoadAdConfig()
            .withAdListener(object : AdListener {
                override fun onError(p0: Ad?, p1: AdError?) {
                    bannerAd.isVisible = false
                    AdsToolkit.logE("onError: ${p1?.errorMessage}")
                }

                override fun onAdLoaded(p0: Ad?) {
                    bannerAd.isVisible = true
                    AdsToolkit.logD("onAdLoaded: BannerAdLoaded")
                }

                override fun onAdClicked(p0: Ad?) {
                    AdsToolkit.logD("onAdClicked: BannerAdClicked")
                }

                override fun onLoggingImpression(p0: Ad?) {
                }

            })
        bannerAd.loadAd(config.build())
    }

    override fun render(container: FrameLayout) {
        container.removeAllViews()
        container.addView(bannerAd)
    }

    override fun destroy() {
        _bannerAd?.removeAllViews()
        _bannerAd?.destroy()
        _bannerAd = null
    }

    override fun onPause() {
    }

    override fun onResume() {
    }
}