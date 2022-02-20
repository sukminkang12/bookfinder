package com.sukminkang.bookfinder.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DetailBookResponseModel(
    var error : Int,
    var title : String,
    var subtitle : String,
    var authors : String,
    var publisher : String,
    var isbn10 : String,
    var isbn13 : String,
    var pages: String,
    var year : String,
    var rating : String,
    var desc : String,
    var price : String,
    var image : String,
    var url : String,
    var pdf: HashMap<String,String>
) : Parcelable