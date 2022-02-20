package com.sukminkang.bookfinder.ui.base

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sukminkang.bookfinder.App
import com.sukminkang.bookfinder.R
import com.sukminkang.bookfinder.data.model.DetailBookResponseModel
import com.sukminkang.bookfinder.databinding.CellPdfItemBinding

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url:String?) {
    url?.let {
        imageView.loadFromUrlString(it)
    }
}

@BindingAdapter(value = ["title","subtitle"], requireAll = true)
fun setTitle(textView:TextView, title:String, subtitle:String) {
    val bookSubtitle = if (subtitle.isBlank()) {
        ""
    } else {
        "(${subtitle})"
    }

    textView.text = "${title}${bookSubtitle}"
}

@BindingAdapter("info")
fun setInfo(textView: TextView, data:DetailBookResponseModel?) {
    data?.let {
        textView.text = String.format(textView.context.getString(R.string.detail_book_screen_info),
        data.title, data.subtitle, data.authors, data.publisher, data.isbn10, data.isbn13,
        data.pages, data.year, data.rating, data.price, data.desc)
    }
}

@BindingAdapter("addPdfList")
fun addPdfList(linearLayout: LinearLayout, pdf:HashMap<String,String>?) {
    App.currentActivity?.let { currentActivity ->
        pdf?.let {
            linearLayout.visibility = View.VISIBLE
            it.iterator().forEach { item ->
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