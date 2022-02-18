package com.sukminkang.bookfinder.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "https://api.itbook.store/1.0/"

    private val retrofitService: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(HttpClient.getHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val searchApi: SearchApiInterface by lazy {
        retrofitService.create(SearchApiInterface::class.java)
    }

    val booksApi: BooksApiInterface by lazy {
        retrofitService.create(BooksApiInterface::class.java)
    }

}