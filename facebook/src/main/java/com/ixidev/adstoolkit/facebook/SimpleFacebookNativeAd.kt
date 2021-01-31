package com.ixidev.adstoolkit.facebook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import com.facebook.ads.*
import com.ixidev.adstoolkit.core.AdsToolkit
import com.ixidev.adstoolkit.core.INativeAd

/**
 * Created by ABDELMAJID ID ALI on 1/23/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
class SimpleFacebookNativeAd : INativeAd {

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
            nativeAdLayout?.isVisible = false
            return
        }
        nativeAd!!.unregisterView()

        nativeAdLayout = container.findViewById(R.id.native_ad_container)
        nativeAdLayout?.isVisible = true

        val inflater = LayoutInflater.from(container.context)

        val adViewLayout = inflater.inflate(
            R.layout.fb_native_ad_item,
            nativeAdLayout, false
        ) as LinearLayout

        val adView = FbHolderAdView(adViewLayout)

        nativeAdLayout!!.addView(adView.root)

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


    class FbHolderAdView(val root: View) {
        // Create native UI using the ad metadata.
        val nativeAdIcon: MediaView = root.findViewById(R.id.native_ad_icon)
        val nativeAdMedia: MediaView = root.findViewById(R.id.native_ad_media)
        private val nativeAdTitle: TextView = root.findViewById(R.id.native_ad_title)
        private val nativeAdSocialContext: TextView =
            root.findViewById(R.id.native_ad_social_context)
        private val nativeAdBody: TextView = root.findViewById(R.id.native_ad_body)
        private val sponsoredLabel: TextView = root.findViewById(R.id.native_ad_sponsored_label)
        private val nativeAdCallToAction: Button = root.findViewById(R.id.native_ad_call_to_action)
        private val adChoicesContainer: LinearLayout = root.findViewById(R.id.ad_choices_container)

        fun bind(context: Context, nativeAd: NativeAd, nativeAdLayout: NativeAdLayout) {
            // Add the AdOptionsView
            val adOptionsView = AdOptionsView(context, nativeAd, nativeAdLayout)
            adChoicesContainer.removeAllViews()
            adChoicesContainer.addView(adOptionsView, 0)

            // Set the Text.
            nativeAdTitle.text = nativeAd.advertiserName
            nativeAdBody.text = nativeAd.adBodyText
            nativeAdSocialContext.text = nativeAd.adSocialContext
            nativeAdCallToAction.visibility =
                if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
            nativeAdCallToAction.text = nativeAd.adCallToAction
            sponsoredLabel.text = nativeAd.sponsoredTranslation
        }

        fun clickableViews(): MutableList<View> {
            return mutableListOf(
                nativeAdTitle,
                nativeAdCallToAction,
                nativeAdBody
            )
        }

    }
}