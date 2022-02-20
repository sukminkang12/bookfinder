package com.sukminkang.bookfinder.ui.component.detailbook

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sukminkang.bookfinder.BookFinderStatus
import com.sukminkang.bookfinder.R
import com.sukminkang.bookfinder.databinding.ActivityBookDetailBinding
import com.sukminkang.bookfinder.ui.base.BaseActivity

class DetailBookActivity : BaseActivity(){

    private lateinit var binding : ActivityBookDetailBinding
    private val bookViewModel: DetailBookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = bookViewModel
    }

    override fun observeViewModel() {
        with (bookViewModel) {
            detailBook.observe(this@DetailBookActivity, {
                binding.mainSv.visibility = View.VISIBLE
                binding.detailBook = it
            })
            goToStore.observe(this@DetailBookActivity, {
                val storeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                startActivity(storeIntent)
            })
            status.observe(this@DetailBookActivity, {
                when (it) {
                    BookFinderStatus.NETWORK_ERROR -> {
                        showToast(baseContext.getString(R.string.common_network_error))
                    }
                    BookFinderStatus.DEFAULT_ERROR -> {
                        showToast(baseContext.getString(R.string.common_default_error))
                    }
                }
            })
        }
    }

    override fun initViewBinding() {
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val isbn = intent.getStringExtra("isbn")!!
        bookViewModel.getBookDetail(isbn)
    }
}