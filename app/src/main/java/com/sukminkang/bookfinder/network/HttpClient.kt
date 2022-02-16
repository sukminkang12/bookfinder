package com.sukminkang.bookfinder.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class HttpClient {

    companion object {
        private const val HTTP_CONNECT_TIMEOUT: Long = 60
        private const val HTTP_WRITE_TIMEOUT: Long = 60
        private const val HTTP_READ_TIMEOUT: Long = 60

        fun getHttpClient(): OkHttpClient {
            var httpClientBuilder = OkHttpClient().newBuilder()
                .retryOnConnectionFailure(false)
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val builder = chain.request().newBuilder()
                    chain.proceed(builder.build())
                }

            return httpClientBuilder.build()
        }
    }
}