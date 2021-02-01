package com.example.demo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds
import com.ixidev.adstoolkit.core.IBannerAd
import com.ixidev.adstoolkit.core.IInterstitialAd
import com.ixidev.adstoolkit.facebook.SimpleFacebookBannerAd
import com.ixidev.adstoolkit.facebook.SimpleFacebookInterstitialAd

class FacebookDemoActivity : AppCompatActivity() {

    private lateinit var banner: IBannerAd
    private lateinit var interstitialAd: IInterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_demo)
        AudienceNetworkAds.initialize(this)
        AdSettings.addTestDevice("5211dd89-8793-4516-a5e7-8583bc4aaedf")
        initBanner()
        initFullScreenAd()
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
        interstitialAd.show {
            Toast.makeText(this, "Ad closed or not loaded", Toast.LENGTH_SHORT).show()
        }
    }
}