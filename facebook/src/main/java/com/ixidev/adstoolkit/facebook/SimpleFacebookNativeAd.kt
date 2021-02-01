package com.ixidev.adstoolkit.facebook

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.facebook.ads.*
import com.ixidev.adstoolkit.core.AdsToolkit
import com.ixidev.adstoolkit.core.INativeAd
import com.ixidev.adstoolkit.core.hide
import com.ixidev.adstoolkit.core.show

/**
 * Created by ABDELMAJID ID ALI on 1/23/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
class SimpleFacebookNativeAd(private val container: FrameLayout) : INativeAd {

    private var nativeAdLayout: NativeAdLayout? = null
    private var nativeAd: NativeAd? = null
    private var adLoaded = false

    override fun load(context: Context, adId: String) {

        if (AudienceNetworkAds.isInitialized(context))
            AudienceNetworkAds.initialize(context)

        nativeAd = NativeAd(context, adId)
        val nativeAdListener = object : NativeAdListener {
            override fun onError(p0: Ad?, p1: AdError?) {
                AdsToolkit.logE("onError: ${p1?.errorMessage}")
            }

            override fun onAdLoaded(ad: Ad?) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return
                }
                // Inflate Native Ad into Container
                //   inflateAd(nativeAd);
                if (ad?.isAdInvalidated == true) {
                    adLoaded = false
                    return
                }
                adLoaded = true
                render(container)
            }

            override fun onAdClicked(p0: Ad?) {
            }

            override fun onLoggingImpression(p0: Ad?) {
            }

            override fun onMediaDownloaded(p0: Ad?) {
            }

        }

        // Request an ad
        nativeAd?.let {
            it.loadAd(
                it.buildLoadAdConfig()
                    .withAdListener(nativeAdListener)
                    .build()
            )
        }

    }

    override fun render(container: FrameLayout) {
        if (!adLoaded) {
            nativeAdLayout?.hide()
            return
        }
        nativeAd!!.unregisterView()

        nativeAdLayout = container.findViewById(R.id.native_ad_container)
        nativeAdLayout?.show()

        val inflater = LayoutInflater.from(container.context)

        val adViewLayout = inflater.inflate(
            R.layout.fb_native_ad_item,
            nativeAdLayout, false
        ) as LinearLayout

        val adView = FbHolderAdView(adViewLayout)

        nativeAdLayout!!.addView(adViewLayout)

        adView.bind(container.context, nativeAd!!, nativeAdLayout!!)

        nativeAd!!.registerViewForInteraction(
            adViewLayout,
            adView.nativeAdMedia,
            adView.nativeAdIcon, adView.clickableViews()
        )
    }

    override fun destroy() {
        //  adViewLayout?.removeAllViews()
        nativeAd?.destroy()
        nativeAdLayout?.removeAllViews()
    }



}