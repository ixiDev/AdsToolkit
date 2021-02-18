package com.example.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.ixidev.adstoolkit.admob.SimpleAdMobBanner
import com.ixidev.adstoolkit.admob.SimpleAdMobInterstitial
import com.ixidev.adstoolkit.admob.SimpleAdMobNative

private const val TAG = "AdMobDemoActivity"

class AdMobDemoActivity : AppCompatActivity() {

    private lateinit var bannerAd: SimpleAdMobBanner
    private lateinit var interstitial: SimpleAdMobInterstitial
    private lateinit var native: SimpleAdMobNative
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admob_demo)
        MobileAds.initialize(this) {
            it.adapterStatusMap.forEach { status ->
                Log.d(TAG, "onCreate:key: ${status.key}")
                Log.d(TAG, "onCreate:description: ${status.value.description}")
                Log.d(TAG, "onCreate:name: ${status.value.initializationState.name}")
            }
        }

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("010A77C308D2A99909D4CE8EF55F2642"))
                .build()
        )
        initBanner()
        initInterstitialAd()
        initNativeAd()
    }

    private fun initNativeAd() {
        native = SimpleAdMobNative(
            AdRequest.Builder(),
            findViewById(R.id.admob_simple_native_ad)
        )
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