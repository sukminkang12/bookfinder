package com.sukminkang.bookfinder.ui.component.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sukminkang.bookfinder.data.DataRepository
import com.sukminkang.bookfinder.data.model.SearchResponseModel
import com.sukminkang.bookfinder.ui.base.BaseViewModel

class SearchViewModel : BaseViewModel() {

    private var currentPage = 1
    private var currentKeyword = ""
    private val _searchInitResult = MutableLiveData<SearchResponseModel>()
    private val _searchNextResult = MutableLiveData<SearchResponseModel>()

    //변수명 바꾸는것도 생각해봐야할듯!
    val searchInitResult : LiveData<SearchResponseModel> get() = _searchInitResult
    val searchNextResult : LiveData<SearchResponseModel> get() = _searchNextResult

    fun checkKeyword(keyword: String) {
        getBookListInit(keyword)
        currentKeyword = keyword
    }

    private fun getBookListInit(keyword: String) {
        currentPage = 1
        addDisposableBag(
            DataRepository.getBookList(keyword,currentPage)
                .subscribe(
                    { resp ->
                        if (resp.error == 0) {
                            _searchInitResult.postValue(resp)
                        }
                    },
                    {

                    }
                )
        )
    }

    fun getNextBookList() {
        currentPage++
        addDisposableBag(
            DataRepository.getBookList(currentKeyword, currentPage)
                .subscribe(
                    { resp ->
                        if (resp.error == 0) {
                            _searchNextResult.postValue(resp)
                        }
                    },
                    {

                    }
                )
        )
    }

}