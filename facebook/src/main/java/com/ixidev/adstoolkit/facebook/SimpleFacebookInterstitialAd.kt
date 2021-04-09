package com.ixidev.adstoolkit.facebook

import android.app.Activity
import android.content.Context
import com.facebook.ads.Ad
import com.facebook.ads.AdError
import com.facebook.ads.InterstitialAd
import com.facebook.ads.InterstitialAdListener
import com.ixidev.adstoolkit.core.FullScreenAdsListener
import com.ixidev.adstoolkit.core.IInterstitialAd

/**
 * Created by ABDELMAJID ID ALI on 1/23/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
class SimpleFacebookInterstitialAd : IInterstitialAd, InterstitialAdListener {

    private var _interstitialAd: InterstitialAd? = null
    private val interstitialAd: InterstitialAd
        get() = _interstitialAd!!
    override var listener: FullScreenAdsListener? = null
    private var loaded: Boolean = false

    override fun load(context: Context, adId: String) {
        this._interstitialAd = InterstitialAd(
            context,
            adId
        )
        interstitialAd.loadAd(
            interstitialAd.buildLoadAdConfig()
                .withAdListener(this)
                .build()
        )
    }


    override fun isLoaded(): Boolean {
        return _interstitialAd != null && interstitialAd.isAdLoaded && !interstitialAd.isAdInvalidated && this.loaded
    }



    override fun destroy() {
        _interstitialAd?.destroy()
        _interstitialAd = null
    }

    override fun show(activity: Activity, listener: FullScreenAdsListener) {
        this.listener = listener
        if (!isLoaded()) {
            this.listener?.onAdDismissed(false)
            return
        }
        interstitialAd.show()
    }

    override fun onError(p0: Ad?, p1: AdError?) {
        listener?.onShowAdFailed(Exception(p1?.errorMessage))
    }

    override fun onAdLoaded(p0: Ad?) {
        this.loaded = true
    }

    override fun onAdClicked(p0: Ad?) {
    }

    override fun onLoggingImpression(p0: Ad?) {

    }

    override fun onInterstitialDisplayed(p0: Ad?) {
        listener?.onAdShowed()
    }

    override fun onInterstitialDismissed(p0: Ad?) {
        p0?.destroy()
        listener?.onAdDismissed()
    }
}