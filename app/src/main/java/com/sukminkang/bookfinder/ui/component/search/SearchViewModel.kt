package com.sukminkang.bookfinder.ui.component.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sukminkang.bookfinder.data.DataRepository
import com.sukminkang.bookfinder.data.model.SearchResponseModel
import com.sukminkang.bookfinder.ui.base.BaseViewModel
import com.sukminkang.bookfinder.ui.base.SingleLiveEvent
import java.lang.Exception

class SearchViewModel : BaseViewModel() {

    enum class KeywordStatus {
        NUMBER_EXCEED,
        NOT_CONTAIN_OPERATOR,
        SINGLE_KEYWORD,
        CONTAIN_OR_OPERATOR,
        CONTAIN_NOT_OPERATOR,
        TOO_MANY_OPERATOR
    }

    private var currentPage = 1
    private var currentKeyword = ""
    private var exceptKeyword = ""
    private val _searchInitResult = MutableLiveData<SearchResponseModel>()
    private val _searchNextResult = MutableLiveData<SearchResponseModel>()
    private val _clickDeleteBtn = SingleLiveEvent<Unit>()
    private val _keywordType = MutableLiveData<KeywordStatus>()

    //변수명 바꾸는것도 생각해봐야할듯!
    val searchInitResult : LiveData<SearchResponseModel> get() = _searchInitResult
    val searchNextResult : LiveData<SearchResponseModel> get() = _searchNextResult
    val clickDeleteBtn : LiveData<Unit> get() = _clickDeleteBtn
    val keywordType : LiveData<KeywordStatus> get() = _keywordType

    fun checkKeyword(keyword: String) {
        var orCount = keyword.count { it == '|' }
        var notCount = keyword.count { it == '-'}

        if (orCount == 0 && notCount == 0) {
            val keywordList = keyword.split(" ")
            when (keywordList.size) {
                1 -> {
                    _keywordType.postValue(KeywordStatus.SINGLE_KEYWORD)
                    getBookListInit(keywordList[0])
                }
                2 -> {
                    _keywordType.postValue(KeywordStatus.NOT_CONTAIN_OPERATOR)
                }
                else -> {
                    _keywordType.postValue(KeywordStatus.NUMBER_EXCEED)
                }
            }
        } else if (orCount == 0) {
            when (notCount) {
                1 -> {
                    _keywordType.postValue(KeywordStatus.CONTAIN_NOT_OPERATOR)
                    val keywordList = keyword.split("-")
                    getBookListInit(keywordList[0],keywordList[1])
                }
                else -> {
                    _keywordType.postValue(KeywordStatus.TOO_MANY_OPERATOR)
                }
            }
        } else if (notCount == 0) {
            when (orCount) {
                1 -> {
                    _keywordType.postValue(KeywordStatus.CONTAIN_OR_OPERATOR)
                } else -> {
                    _keywordType.postValue(KeywordStatus.TOO_MANY_OPERATOR)
                }
            }
        } else {
            _keywordType.postValue(KeywordStatus.TOO_MANY_OPERATOR)
        }
    }

    private fun getBookList() {
        addDisposableBag(
            DataRepository.getBookList(currentKeyword, currentPage)
                .subscribe(
                    { resp ->
                        if (resp.error == 0) {
                            if (exceptKeyword.isNotBlank()) {
                                resp.books.removeAll {
                                    !it.title.lowercase().contains(currentKeyword) || it.title.lowercase().contains(exceptKeyword)
                                }
                            }
                            if (currentPage == 1) {
                                _searchInitResult.postValue(resp)
                            } else {
                                _searchNextResult.postValue(resp)
                            }
                        }
                    },
                    {

                    }
                )
        )
    }

    private fun getBookListInit(keyword:String, except:String = "") {
        currentPage = 1
        currentKeyword = keyword.lowercase()
        exceptKeyword = except.lowercase()
        getBookList()
    }

    fun getNextBookList() {
        currentPage++
        getBookList()
    }

    fun onDeleteBtnClick() {
        currentKeyword = ""
        _clickDeleteBtn.call()
    }
}