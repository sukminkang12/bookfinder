package com.sukminkang.bookfinder.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponseModel(
    var error:Int,
    var total:Int,
    var page:Int,
    var books:ArrayList<SearchBooksModel>
):Parcelable

@Parcelize
data class SearchBooksModel(
    var title:String,
    var subtitle:String,
    var isbn13:String,
    var price:String,
    var image:String,
    var url:String,
):Parcelable