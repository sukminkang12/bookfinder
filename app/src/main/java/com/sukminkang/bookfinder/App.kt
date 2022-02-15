package com.sukminkang.bookfinder

import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        lateinit var globalContext : Context
    }

    override fun onCreate() {
        globalContext = this
        super.onCreate()
    }
}