package com.sukminkang.bookfinder.network

import com.sukminkang.bookfinder.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class HttpClient {

    companion object {
        private const val HTTP_CONNECT_TIMEOUT: Long = 60
        private const val HTTP_WRITE_TIMEOUT: Long = 60
        private const val HTTP_READ_TIMEOUT: Long = 60

        fun getHttpClient(): OkHttpClient {
            val httpClientBuilder = OkHttpClient().newBuilder()
                .retryOnConnectionFailure(false)
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                httpClientBuilder.addInterceptor(loggingInterceptor)
            }

            return httpClientBuilder.build()
        }
    }
}