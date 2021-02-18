package com.ixidev.adstoolkit.facebook

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.facebook.ads.AdOptionsView
import com.facebook.ads.MediaView
import com.facebook.ads.NativeAd
import com.facebook.ads.NativeAdLayout

/**
 * Created by ABDELMAJID ID ALI on 2/1/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */

class FbHolderAdView(@Suppress("MemberVisibilityCanBePrivate") val root: View) {
    // Create native UI using the ad metadata.
    val nativeAdIcon: MediaView = root.findViewById(R.id.native_ad_icon)
    val nativeAdMedia: MediaView = root.findViewById(R.id.native_ad_media)
    private val nativeAdTitle: TextView = root.findViewById(R.id.native_ad_title)
    private val nativeAdSocialContext: TextView =
        root.findViewById(R.id.native_ad_social_context)
    private val nativeAdBody: TextView = root.findViewById(R.id.native_ad_body)
    private val sponsoredLabel: TextView = root.findViewById(R.id.native_ad_sponsored_label)
    private val nativeAdCallToAction: AppCompatButton = root.findViewById(R.id.native_ad_call_to_action)
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