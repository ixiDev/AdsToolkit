package com.example.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.ixidev.adstoolkit.admob.SimpleAdMobBanner
import com.ixidev.adstoolkit.admob.SimpleAdMobInterstitial
import com.ixidev.adstoolkit.admob.SimpleAdMobNative

class AdMobDemoActivity : AppCompatActivity() {

    private lateinit var bannerAd: SimpleAdMobBanner
    private lateinit var interstitial: SimpleAdMobInterstitial
    private lateinit var native: SimpleAdMobNative
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob_demo)
        initBanner()
        initInterstitialAd()
        initNativeAd()
    }

    private fun initNativeAd() {
        native = SimpleAdMobNative(AdRequest.Builder(), findViewById(R.id.admob_simple_native_ad))
        native.load(this, "ca-app-pub-3940256099942544/2247696110")
    }

    private fun initInterstitialAd() {
        interstitial = SimpleAdMobInterstitial(AdRequest.Builder())
        interstitial.load(this, "ca-app-pub-3940256099942544/1033173712")
    }

    private fun initBanner() {
        bannerAd = SimpleAdMobBanner(
            AdRequest.Builder()
        )
        bannerAd.load(this, "ca-app-pub-3940256099942544/6300978111")
        bannerAd.render(findViewById(R.id.admob_simple_banner))
    }

    fun showFullScreenAd(@Suppress("UNUSED_PARAMETER") view: View) {
        interstitial.show(this) {
            Toast.makeText(this, "ad closed", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        bannerAd.destroy()
    }

    override fun onPause() {
        super.onPause()
        bannerAd.onPause()
    }

    override fun onResume() {
        super.onResume()
        bannerAd.onResume()
    }
}