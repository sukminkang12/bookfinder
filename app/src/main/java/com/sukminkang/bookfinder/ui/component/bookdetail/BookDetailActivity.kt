package com.sukminkang.bookfinder.ui.component.bookdetail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sukminkang.bookfinder.databinding.ActivityBookDetailBinding
import com.sukminkang.bookfinder.ui.base.BaseActivity

class BookDetailActivity : BaseActivity(){

    private lateinit var binding : ActivityBookDetailBinding
    private val viewModel: BookDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }

    override fun observeViewModel() {
        with (viewModel) {
            bookDetail.observe(this@BookDetailActivity, {
                binding.mainSv.visibility = View.VISIBLE
                binding.bookDetail = it
            })
        }
    }

    override fun initViewBinding() {
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val isbn = intent.getStringExtra("isbn")!!
        viewModel.getBookDetail(isbn)
    }
}