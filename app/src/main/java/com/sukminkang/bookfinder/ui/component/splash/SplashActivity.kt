package com.sukminkang.bookfinder.ui.component.splash

import android.os.Bundle
import com.sukminkang.bookfinder.databinding.ActivitySplashBinding
import com.sukminkang.bookfinder.ui.base.BaseActivity

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
    }

}