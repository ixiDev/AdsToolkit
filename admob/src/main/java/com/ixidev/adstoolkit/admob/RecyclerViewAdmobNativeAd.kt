package com.ixidev.adstoolkit.admob

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresPermission
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.ixidev.adstoolkit.core.INativeAd

/**
 * Created by ABDELMAJID ID ALI on 3/19/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */
class RecyclerViewAdmobNativeAd(
    private val adRequest: AdRequest.Builder
) : INativeAd {

    private var currentNativeAd: NativeAd? = null

    var activityDestroyed: Boolean = false
    var isFinishing: Boolean = false
    var isChangingConfigurations: Boolean = false
    private var container: FrameLayout? = null

    @SuppressLint("InflateParams")
    @RequiresPermission("android.permission.INTERNET")
    override fun load(context: Context, adId: String) {

        val builder = AdLoader.Builder(context, adId)
        builder.forNativeAd { nativeAd ->
            if (activityDestroyed || isFinishing || isChangingConfigurations) {
                nativeAd.destroy()
                return@forNativeAd
            }
            // You must call destroy on old ads when you are done with them,
            // otherwise you will have a memory leak.
            currentNativeAd?.destroy()
            currentNativeAd = nativeAd
            inflateNativeAd()
        }
        builder.build()
            .loadAd(adRequest.build())
    }

    private fun inflateNativeAd() {
        if (container == null || currentNativeAd == null)
            return
        val adView = LayoutInflater.from(this.container!!.context)
            .inflate(R.layout.ad_unified, null) as NativeAdView
        populateNativeAdView(this.currentNativeAd!!, adView)
        this.container!!.removeAllViews()
        this.container!!.addView(adView)
    }

    override fun render(container: FrameLayout) {
        this.container = container
        inflateNativeAd()
    }

    override fun destroy() {
        currentNativeAd?.destroy()
        currentNativeAd = null
        activityDestroyed = true
        container?.removeAllViews()
    }


    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        // Set the media view.
        adView.mediaView = adView.findViewById(R.id.ad_media)
        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        adView.mediaView?.setMediaContent(nativeAd.mediaContent!!)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView?.visibility = View.INVISIBLE
        } else {
            adView.bodyView?.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView?.visibility = View.INVISIBLE
        } else {
            adView.callToActionView?.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView?.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon?.drawable
            )
            adView.iconView?.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView?.visibility = View.INVISIBLE
        } else {
            adView.priceView?.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView?.visibility = View.INVISIBLE
        } else {
            adView.storeView?.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView?.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView?.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView?.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView?.visibility = View.VISIBLE
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)


    }

}