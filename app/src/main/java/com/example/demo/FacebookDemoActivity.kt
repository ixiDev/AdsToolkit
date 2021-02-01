package com.example.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ixidev.adstoolkit.core.IBannerAd
import com.ixidev.adstoolkit.facebook.SimpleFacebookBannerAd

class FacebookDemoActivity : AppCompatActivity() {

    private lateinit var banner: IBannerAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_demo)
        initBanner()
    }

    private fun initBanner() {
        banner = SimpleFacebookBannerAd()
        banner.load(this, "1444710325874143_1444712852540557")
        banner.render(findViewById(R.id.fb_simple_banner))
    }

    @Suppress("UNUSED_PARAMETER")
    fun showFullScreenAd(view: View) {
    }
}