package com.sukminkang.bookfinder

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.sukminkang.bookfinder.ui.base.BaseActivity
import com.sukminkang.bookfinder.ui.component.error.ErrorActivity
import kotlin.system.exitProcess

class App : Application() {
    companion object {
        lateinit var globalContext : Context
        var currentActivity: BaseActivity?=null
    }

    override fun onCreate() {
        super.onCreate()
        val defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        val customExceptionHandler = DefaultExceptionHandler(defaultExceptionHandler)
        Thread.setDefaultUncaughtExceptionHandler(customExceptionHandler)

        globalContext = this

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
                if (activity is BaseActivity) {
                    currentActivity = activity
                }
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                if (currentActivity == activity) {
                    currentActivity = null
                }
            }

        })
    }

    class DefaultExceptionHandler (private val  defaultExceptionHandler:Thread.UncaughtExceptionHandler?) : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(thread: Thread, ex: Throwable) {
            val errorIntent = Intent(globalContext, ErrorActivity::class.java)
            errorIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            currentActivity!!.startActivity(errorIntent)
            exitProcess(0)
        }
    }
}