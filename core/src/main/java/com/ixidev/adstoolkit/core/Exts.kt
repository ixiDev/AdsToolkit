package com.ixidev.adstoolkit.core

import android.view.View
import androidx.core.view.isVisible

/**
 * Created by ABDELMAJID ID ALI on 2/1/21.
 * Email : abdelmajid.idali@gmail.com
 * Github : https://github.com/ixiDev
 */


fun View.hide() {
    this.isVisible = false
}

fun View.show() {
    this.isVisible = true
}