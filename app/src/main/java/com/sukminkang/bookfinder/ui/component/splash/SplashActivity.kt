package com.sukminkang.bookfinder.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sukminkang.bookfinder.SPLASH_DELAY
import com.sukminkang.bookfinder.databinding.ActivitySplashBinding
import com.sukminkang.bookfinder.ui.base.BaseActivity
import com.sukminkang.bookfinder.ui.component.search.SearchActivity

class SplashActivity : BaseActivity(){

    private lateinit var binding : ActivitySplashBinding

    override fun observeViewModel() {
    }

    override fun initViewBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.myLooper()!!).postDelayed({
            val nextScreenIntent = Intent(this, SearchActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, SPLASH_DELAY)
    }

}