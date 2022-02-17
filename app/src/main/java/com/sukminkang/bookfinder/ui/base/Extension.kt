package com.sukminkang.bookfinder.ui.base

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadFromUrlString(url: String) {
    if (url.isNotBlank()) {
        Picasso.get().load(url).into(this)
    }
}