package com.sukminkang.bookfinder.ui.component.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukminkang.bookfinder.BookFinderStatus
import com.sukminkang.bookfinder.R
import com.sukminkang.bookfinder.databinding.ActivitySearchBinding
import com.sukminkang.bookfinder.ui.base.BaseActivity
import com.sukminkang.bookfinder.ui.base.hideKeyboard
import com.sukminkang.bookfinder.ui.component.detailbook.DetailBookActivity

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
                if (it.isEmpty()) {
                    binding.notFoundCl.visibility = View.VISIBLE
                    binding.searchList.visibility = View.GONE
                } else {
                    binding.notFoundCl.visibility = View.GONE
                    binding.searchList.visibility = View.VISIBLE
                    mainAdapter.initList(it)
                }
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
            status.observe(this@SearchActivity, {
                when (it) {
                    BookFinderStatus.TOO_MANY_OPERATOR -> {
                        showToast(baseContext.getString(R.string.search_screen_too_many_operator))
                    }
                    BookFinderStatus.NUMBER_EXCEED -> {
                        showToast(baseContext.getString(R.string.search_screen_too_many_keyword))
                    }
                    BookFinderStatus.NOT_CONTAIN_OPERATOR -> {
                        showToast(baseContext.getString(R.string.search_screen_must_operator))
                    }
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
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainAdapter = SearchListAdapter()
        binding.searchList.layoutManager = LinearLayoutManager(baseContext)
        binding.searchList.adapter = mainAdapter

        binding.searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.checkKeyword(v.editableText.toString())
                binding.searchEditText.hideKeyboard()
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
        val bookDetailIntent = Intent(this, DetailBookActivity::class.java)
        bookDetailIntent.putExtra("isbn",isbn)
        startActivity(bookDetailIntent)
    }
}