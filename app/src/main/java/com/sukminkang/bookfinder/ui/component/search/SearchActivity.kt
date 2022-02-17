package com.sukminkang.bookfinder.ui.component.search

import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sukminkang.bookfinder.R
import com.sukminkang.bookfinder.databinding.ActivitySearchBinding
import com.sukminkang.bookfinder.ui.base.BaseActivity

class SearchActivity : BaseActivity() {

    private lateinit var binding : ActivitySearchBinding
    private lateinit var mainAdapter: SearchListAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun observeViewModel() {
        with (viewModel) {
            searchInitResult.observe(this@SearchActivity , {
                binding.totalText.text = "${baseContext.getString(R.string.search_screen_total)} : ${it.total}"
                mainAdapter.initList(it.books)
            })
            searchNextResult.observe(this@SearchActivity, {
                mainAdapter.addList(it.books)
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
        }

    }
}