package com.ixidev.adstoolkit.admob

import android.app.Activity
import android.content.Context
import androidx.annotation.RequiresPermission
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ixidev.adstoolkit.core.AdsToolkit
import com.ixidev.adstoolkit.core.FullScreenAdsListener
import com.ixidev.adstoolkit.core.IInterstitialAd

/**
 * Created by ABDELMAJID ID ALI on 3/20/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
class ClickableAdMobInterstitialAd(private val adRequest: AdRequest.Builder) : IInterstitialAd {

    private var _interstitialAd: InterstitialAd? = null
    private val interstitialAd: InterstitialAd
        get() = _interstitialAd!!
    private var loadCallback: InterstitialAdLoadCallback? = null
    private var fullScreenContentCallback: FullScreenContentCallback? = null

    var impressionListener: ImpressionListener? = null

    @RequiresPermission("android.permission.INTERNET")
    override fun load(context: Context, adId: String) {

        initLoadCallback()
        InterstitialAd.load(
            context,
            adId,
            adRequest.build(),
            loadCallback!!
        )
    }

    private fun initLoadCallback() {
        loadCallback = object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                _interstitialAd = ad
                initFullScreenCallBack()
                AdsToolkit.logD("${this@ClickableAdMobInterstitialAd.javaClass.name} : loaded")
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                _interstitialAd = null
                AdsToolkit.logE(
                    "Error ${this@ClickableAdMobInterstitialAd.javaClass.name} :",
                    Exception(p0.message)
                )
            }
        }
    }

    private fun initFullScreenCallBack() {
        fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                listener?.onAdDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError?) {
                listener?.onShowAdFailed(Exception(p0?.message))
            }

            override fun onAdShowedFullScreenContent() {
                listener?.onAdShowed()
            }

            override fun onAdImpression() {
                impressionListener?.onAdImpression()
            }
        }
        interstitialAd.fullScreenContentCallback = fullScreenContentCallback
    }


    override fun show(activity: Activity, listener: FullScreenAdsListener) {
        this.listener = listener
        if (!isLoaded()) {
            listener.onAdDismissed(false)
            return
        }
        interstitialAd.show(activity)
    }

    override var listener: FullScreenAdsListener? = null

    override fun isLoaded(): Boolean {
        return _interstitialAd != null
    }


    override fun destroy() {
        _interstitialAd?.fullScreenContentCallback = null
        _interstitialAd = null
        fullScreenContentCallback = null
        loadCallback = null
        listener = null
        impressionListener = null
    }

    interface ImpressionListener {
        fun onAdImpression()
    }
}