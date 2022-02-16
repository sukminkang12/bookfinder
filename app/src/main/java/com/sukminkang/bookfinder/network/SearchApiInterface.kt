package com.sukminkang.bookfinder.network

import com.sukminkang.bookfinder.data.model.SearchResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


@JvmSuppressWildcards
interface SearchApiInterface {

    @GET("search/{query}/{page}")
    fun getBookList(@Path("query") query:String, @Path("page") page:Int) : Single<SearchResponseModel>
}