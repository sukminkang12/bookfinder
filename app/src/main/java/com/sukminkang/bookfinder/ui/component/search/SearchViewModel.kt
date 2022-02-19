package com.sukminkang.bookfinder.ui.component.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sukminkang.bookfinder.data.DataRepository
import com.sukminkang.bookfinder.data.model.SearchBooksModel
import com.sukminkang.bookfinder.data.model.SearchResponseModel
import com.sukminkang.bookfinder.ui.base.BaseViewModel
import com.sukminkang.bookfinder.ui.base.SingleLiveEvent
import io.reactivex.Observable
import java.util.*
import kotlin.math.max

class SearchViewModel : BaseViewModel() {

    enum class KeywordStatus {
        NUMBER_EXCEED,
        NOT_CONTAIN_OPERATOR,
        SINGLE_KEYWORD,
        OR_OPERATOR,
        NOT_OPERATOR,
        TOO_MANY_OPERATOR
    }

    private var currentPage = 1
    private var currentKeyword = ""
    private var exceptKeyword = ""
    private var currentFirstKeyword = ""
    private var currentSecondKeyword = ""
    private var firstKeywordMaximumPage = Int.MAX_VALUE
    private var secondKeywordMinimumPage = Int.MAX_VALUE
    private val _searchInitResult = MutableLiveData<ArrayList<SearchBooksModel>>()
    private val _searchNextResult = MutableLiveData<ArrayList<SearchBooksModel>>()
    private val _clickDeleteBtn = SingleLiveEvent<Unit>()
    private val _keywordType = MutableLiveData<KeywordStatus>()

    //변수명 바꾸는것도 생각해봐야할듯!
    val searchInitResult : LiveData<ArrayList<SearchBooksModel>> get() = _searchInitResult
    val searchNextResult : LiveData<ArrayList<SearchBooksModel>> get() = _searchNextResult
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
                    _keywordType.postValue(KeywordStatus.NOT_OPERATOR)
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
                    _keywordType.postValue(KeywordStatus.OR_OPERATOR)
                    val keywordList = keyword.split("|")
                    getBookListZipInit(keywordList[0], keywordList[1])
                } else -> {
                    _keywordType.postValue(KeywordStatus.TOO_MANY_OPERATOR)
                }
            }
        } else {
            _keywordType.postValue(KeywordStatus.TOO_MANY_OPERATOR)
        }
    }

    private fun getBookList() {
        if (currentPage > 100) return

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
                                _searchInitResult.postValue(resp.books)
                            } else {
                                _searchNextResult.postValue(resp.books)
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
        currentKeyword = keyword.lowercase().replace(" ","")
        exceptKeyword = except.lowercase().replace(" ","")
        getBookList()
    }

    private fun getBookListZipInit(keyword1:String, keyword2:String) {
        currentPage = 1
        currentFirstKeyword = keyword1
        currentSecondKeyword = keyword2
        getBookListZip()
    }

    private fun getBookListZip() {
        if (currentPage > 100) return

        if (currentPage <= firstKeywordMaximumPage && currentPage <= secondKeywordMinimumPage) {
            addDisposableBag(Observable.zip(
                DataRepository.getBookList(currentFirstKeyword, currentPage),
                DataRepository.getBookList(currentSecondKeyword, currentPage),
                {   resp1, resp2->
                        Pair(resp1, resp2)
                })
                .subscribe({ pair ->
                    if (pair.first.error == 0 && pair.second.error == 0) {
                        val searchList = (pair.first.books + pair.second.books).distinctBy { it.isbn13 }
                        if (currentPage == 1) {
                            firstKeywordMaximumPage = max(0, pair.first.total - 1) / 10 + 1
                            secondKeywordMinimumPage = max(0, pair.second.total - 1) / 10 + 1
                            _searchInitResult.postValue(ArrayList(searchList))
                        } else {
                            _searchNextResult.postValue(ArrayList(searchList))
                        }
                    }
                }
                ,{

                    })
            )
        } else if (currentPage <= firstKeywordMaximumPage) {
            currentKeyword = currentFirstKeyword
            getBookList()
        } else if (currentPage <= secondKeywordMinimumPage) {
            currentKeyword = currentSecondKeyword
            getBookList()
        }
    }

    fun getNextBookList() {
        currentPage++
        when (keywordType.value) {
            KeywordStatus.OR_OPERATOR -> {
                getBookListZip()
            }
            KeywordStatus.NOT_CONTAIN_OPERATOR, KeywordStatus.SINGLE_KEYWORD -> {
                getBookList()
            }
        }
    }

    fun onDeleteBtnClick() {
        currentKeyword = ""
        _clickDeleteBtn.call()
    }
}