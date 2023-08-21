package com.example.rcg.util

import android.app.Activity
import android.view.View
import com.bumptech.glide.RequestManager

fun RequestManager.release(view: View) {
    if ((view.context as Activity)?.isDestroyed?.not() == true) {
        clear(view)
    }
}