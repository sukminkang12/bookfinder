package com.sukminkang.bookfinder.ui.base

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url:String) {
    imageView.loadFromUrlString(url)
}

@BindingAdapter(value = ["title","subtitle"], requireAll = true)
fun setTitle(textView:TextView, title:String, subtitle:String) {
    var bookSubtitle = if (subtitle.isBlank()) {
        ""
    } else {
        "(${subtitle})"
    }

    textView.text = "${title}${bookSubtitle}"
}