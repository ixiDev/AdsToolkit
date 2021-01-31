package com.ixidev.adstoolkit.facebook

import android.content.Context
import com.facebook.ads.Ad
import com.facebook.ads.InterstitialAd
import com.ixidev.adstoolkit.core.IInterstitialAd
import com.ixidev.adstoolkit.core.OnInterstitialClosed

/**
 * Created by ABDELMAJID ID ALI on 1/23/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
class SimpleFacebookInterstitialAd : IInterstitialAd {

    private var onInterstitialClosed: OnInterstitialClosed? = null
    private var _interstitialAd: InterstitialAd? = null
    private val interstitialAd: InterstitialAd
        get() = _interstitialAd!!

    override fun load(context: Context, adId: String) {
        if (_interstitialAd == null) {
            this._interstitialAd = InterstitialAd(
                context,
                adId
            )
            interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                    .withAdListener(object : FullScreenAdListener() {
                        override fun onInterstitialDismissed(p0: Ad?) {
                            onInterstitialClosed?.invoke()
                        }

                        override fun onInterstitialDisplayed(p0: Ad?) {
                        }

                        override fun onLoggingImpression(p0: Ad?) {
                        }

                        override fun onAdClicked(p0: Ad?) {
                        }
                    })
                    .build()
            )
        }
    }


    override fun isLoaded(): Boolean {
        return _interstitialAd != null && interstitialAd.isAdLoaded && !interstitialAd.isAdInvalidated
    }

    override fun show(onInterstitialClosed: OnInterstitialClosed) {
        this.onInterstitialClosed = onInterstitialClosed
        if (!isLoaded()) {
            onInterstitialClosed.invoke()
            return
        }
        interstitialAd.show()
    }

    override fun destroy() {
        _interstitialAd?.destroy()
        _interstitialAd = null
    }
}