package com.sukminkang.bookfinder.ui.component.error

import com.sukminkang.bookfinder.databinding.ActivityErrorBinding
import com.sukminkang.bookfinder.ui.base.BaseActivity

class ErrorActivity : BaseActivity() {

    private lateinit var binding : ActivityErrorBinding

    override fun observeViewModel() {

    }

    override fun initViewBinding() {
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}