package com.ixidev.adstoolkit.admob

import android.app.Activity
import android.content.Context
import androidx.annotation.RequiresPermission
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ixidev.adstoolkit.core.AdsToolkit
import com.ixidev.adstoolkit.core.FullScreenAdsListener
import com.ixidev.adstoolkit.core.IInterstitialAd
import com.ixidev.adstoolkit.core.OnInterstitialClosed

@Suppress("unused")
class SimpleAdMobInterstitial(private val adRequest: AdRequest.Builder) : IInterstitialAd {

    private var _interstitialAd: InterstitialAd? = null
    private val interstitialAd: InterstitialAd
        get() = _interstitialAd!!

    @RequiresPermission("android.permission.INTERNET")
    override fun load(context: Context, adId: String) {
        InterstitialAd.load(
            context,
            adId,
            adRequest.build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    _interstitialAd = ad
                    initFullScreenCallBack()
                    AdsToolkit.logD("${this@SimpleAdMobInterstitial.javaClass.name} : loaded")
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    _interstitialAd = null
                    AdsToolkit.logE(
                        "Error ${this@SimpleAdMobInterstitial.javaClass.name} :",
                        Exception(p0.message)
                    )
                }
            }
        )
    }

    private fun initFullScreenCallBack() {
        interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                listener?.onAdDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError?) {
                listener?.onShowAdFailed(Exception(p0?.message))
            }

            override fun onAdShowedFullScreenContent() {
                listener?.onAdShowed()
            }
        }
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

    override fun show(onInterstitialClosed: OnInterstitialClosed) {
        error("Please call activity: Activity, onInterstitialClosed: OnInterstitialClosed")
    }


    override fun destroy() {
        _interstitialAd?.fullScreenContentCallback = null
        _interstitialAd = null
    }
}