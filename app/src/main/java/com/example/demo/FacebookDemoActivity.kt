package com.example.demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds
import com.ixidev.adstoolkit.core.FullScreenAdsListener
import com.ixidev.adstoolkit.core.IBannerAd
import com.ixidev.adstoolkit.core.IInterstitialAd
import com.ixidev.adstoolkit.core.INativeAd
import com.ixidev.adstoolkit.facebook.SimpleFacebookBannerAd
import com.ixidev.adstoolkit.facebook.SimpleFacebookInterstitialAd
import com.ixidev.adstoolkit.facebook.SimpleFacebookNativeAd

class FacebookDemoActivity : AppCompatActivity(), FullScreenAdsListener {

    private lateinit var banner: IBannerAd
    private lateinit var interstitialAd: IInterstitialAd
    private lateinit var nativeAd: INativeAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_demo)
        AudienceNetworkAds.initialize(this)
        AdSettings.addTestDevice("5211dd89-8793-4516-a5e7-8583bc4aaedf")
        initBanner()
        initFullScreenAd()
        initNativeAd()
    }

    private fun initNativeAd() {
        nativeAd = SimpleFacebookNativeAd(findViewById(R.id.fb_simple_native_ad))
        nativeAd.load(this, "1444710325874143_1444720032539839")
    }

    private fun initFullScreenAd() {
        interstitialAd = SimpleFacebookInterstitialAd()
        interstitialAd.load(this, "1444710325874143_1444718842539958")
    }

    private fun initBanner() {
        banner = SimpleFacebookBannerAd()
        banner.load(this, "1444710325874143_1444712852540557")
        banner.render(findViewById(R.id.fb_simple_banner))
    }

    @Suppress("UNUSED_PARAMETER")
    fun showFullScreenAd(view: View) {
        interstitialAd.show(this, this)
    }

    override fun onAdDismissed(isShowed: Boolean) {
        if (isShowed)
            Toast.makeText(this, "Ad closed", Toast.LENGTH_SHORT).show()
    }

    override fun onShowAdFailed(error: Exception) {
        Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onAdShowed() {
        Toast.makeText(this, "Ad displayed", Toast.LENGTH_SHORT).show()
    }
}