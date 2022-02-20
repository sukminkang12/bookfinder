package com.sukminkang.bookfinder.ui.base

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sukminkang.bookfinder.App
import com.sukminkang.bookfinder.databinding.CellPdfItemBinding

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url:String?) {
    url?.let {
        imageView.loadFromUrlString(it)
    }
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

@BindingAdapter("addPdfList")
fun addPdfList(linearLayout: LinearLayout, pdf:HashMap<String,String>? = hashMapOf()) {
    App.currentActivity?.let { currentActivity ->
        pdf?.let {
            linearLayout.visibility = View.VISIBLE
            it.iterator()?.forEach { item ->
                val itemBinding = CellPdfItemBinding.inflate(currentActivity.layoutInflater)
                itemBinding.chapter.text = item.key
                itemBinding.root.setOnClickListener {
                    val pdfIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.value))
                    currentActivity.startActivity(pdfIntent)
                }
                linearLayout.addView(itemBinding.root)
            }
        }
    }
}