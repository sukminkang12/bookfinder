package com.sukminkang.bookfinder.ui.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

fun ImageView.loadFromUrlString(url: String) {
    if (url.isNotBlank()) {
        Glide.with(this.context)
            .load(url)
            .thumbnail(0.1f)
            .into(this)
    }
}

fun EditText.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}