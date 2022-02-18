package com.sukminkang.bookfinder.network

import com.sukminkang.bookfinder.data.model.BookDetailResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

@JvmSuppressWildcards
interface BooksApiInterface {

    @GET("books/{isbn}")
    fun getBookDetail(@Path("isbn") isbn:String) : Single<BookDetailResponseModel>
}