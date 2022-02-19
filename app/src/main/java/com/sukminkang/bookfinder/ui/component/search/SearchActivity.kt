package com.sukminkang.bookfinder.ui.component.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukminkang.bookfinder.R
import com.sukminkang.bookfinder.databinding.ActivitySearchBinding
import com.sukminkang.bookfinder.ui.base.BaseActivity
import com.sukminkang.bookfinder.ui.base.hideKeyboard
import com.sukminkang.bookfinder.ui.base.loadFromUrlString
import com.sukminkang.bookfinder.ui.component.bookdetail.BookDetailActivity

class SearchActivity : BaseActivity() {

    private lateinit var binding : ActivitySearchBinding
    private lateinit var mainAdapter: SearchListAdapter
    private val viewModel: SearchViewModel by viewModels()
    private var backPressedAt = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
    }

    override fun observeViewModel() {
        with (viewModel) {
            searchInitResult.observe(this@SearchActivity , {
                mainAdapter.initList(it)
            })
            searchNextResult.observe(this@SearchActivity, {
                mainAdapter.addList(it)
            })
            clickDeleteBtn.observe(this@SearchActivity, {
                binding.searchEditText.setText("")
                binding.searchEditText.hideKeyboard()
                binding.searchEditText.clearFocus()
            })
            progressBarStatus.observe(this@SearchActivity, {
                binding.progress.visibility = it
            })
        }
    }

    override fun initViewBinding() {
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainAdapter = SearchListAdapter()
        binding.searchList.layoutManager = LinearLayoutManager(baseContext)
        binding.searchList.adapter = mainAdapter

        binding.searchEditText.setOnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.checkKeyword(v.editableText.toString())
                true
            }
            false
        }

        mainAdapter.run {
            requestNextPageCallback = {
                viewModel.getNextBookList()
            }
            onItemClickCallback = {
                goBookDetail(it)
            }
        }
    }

    override fun onBackPressed() {
        if (backPressedAt + 2000L > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            showToast(baseContext.getString(R.string.app_back_press_notice))
            backPressedAt = System.currentTimeMillis()
        }
    }

    private fun goBookDetail(isbn:String) {
        val bookDetailIntent = Intent(this, BookDetailActivity::class.java)
        bookDetailIntent.putExtra("isbn",isbn)
        startActivity(bookDetailIntent)
    }
}