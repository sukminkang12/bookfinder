package com.sukminkang.bookfinder.ui.component.search

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sukminkang.bookfinder.BookFinderStatus
import com.sukminkang.bookfinder.data.DataRepository
import com.sukminkang.bookfinder.data.model.SearchBooksModel
import com.sukminkang.bookfinder.ui.base.BaseViewModel
import com.sukminkang.bookfinder.ui.base.SingleLiveEvent
import io.reactivex.Observable
import java.util.*
import kotlin.math.max

class SearchViewModel : BaseViewModel() {

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
    private val _progressBarStatus = MutableLiveData(View.GONE)

    //변수명 바꾸는것도 생각해봐야할듯!
    val searchInitResult : LiveData<ArrayList<SearchBooksModel>> get() = _searchInitResult
    val searchNextResult : LiveData<ArrayList<SearchBooksModel>> get() = _searchNextResult
    val clickDeleteBtn : LiveData<Unit> get() = _clickDeleteBtn
    val progressBarStatus : LiveData<Int> get() = _progressBarStatus

    fun checkKeyword(keyword: String) {
        if (keyword.isBlank()) {
            status.postValue(BookFinderStatus.BLANK_KEYWORD)
            return
        }

        val orCount = keyword.count { it == '|' }
        val notCount = keyword.count { it == '-'}
        var keywordList = keyword.split("[-,|,' ']".toRegex()).filter { it.isNotBlank() }

        if (keywordList.size > 2) {
            status.postValue(BookFinderStatus.NUMBER_EXCEED)
            return
        }

        when (orCount + notCount) {
            0 -> {
                if (keywordList.size == 1) {
                    status.postValue(BookFinderStatus.SINGLE_KEYWORD)
                    getBookListInit(keywordList[0])
                } else {
                    status.postValue(BookFinderStatus.NOT_CONTAIN_OPERATOR)
                }
            }
            1 -> {
                if (keywordList.size == 1) {
                    status.postValue(BookFinderStatus.NOT_TWO_KEYWORD)
                    return
                }
                if (orCount == 1) {
                    status.postValue(BookFinderStatus.OR_OPERATOR)
                    getBookListZipInit(keywordList[0], keywordList[1])
                } else {
                    status.postValue(BookFinderStatus.NOT_OPERATOR)
                    getBookListInit(keywordList[0], keywordList[1])
                }
            }
            else -> {
                status.postValue(BookFinderStatus.TOO_MANY_OPERATOR)
            }
        }
    }

    private fun getBookList() {
        if (currentPage > 100) return

        addDisposableBag(
            DataRepository.getBookList(currentKeyword, currentPage)
                .doOnTerminate {
                    _progressBarStatus.postValue(View.GONE)
                }
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
                        } else {
                            status.postValue(BookFinderStatus.DEFAULT_ERROR)
                        }
                    },
                    {
                        handleError(it)
                    }
                )
        )
    }

    private fun setInit() {
        _progressBarStatus.postValue(View.VISIBLE)
        currentPage = 1
    }

    private fun getBookListInit(keyword:String, except:String = "") {
        setInit()
        currentKeyword = keyword.lowercase().replace(" ","")
        exceptKeyword = except.lowercase().replace(" ","")
        getBookList()
    }

    private fun getBookListZipInit(keyword1:String, keyword2:String) {
        setInit()
        currentFirstKeyword = keyword1.lowercase().replace(" ", "")
        currentSecondKeyword = keyword2.lowercase().replace(" ","")
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
                .doOnTerminate {
                    _progressBarStatus.postValue(View.GONE)
                }
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
                    } else {
                        status.postValue(BookFinderStatus.DEFAULT_ERROR)
                    }
                }
                ,{
                    handleError(it)
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
        when (status.value) {
            BookFinderStatus.OR_OPERATOR -> {
                getBookListZip()
            }
            BookFinderStatus.NOT_OPERATOR, BookFinderStatus.SINGLE_KEYWORD -> {
                getBookList()
            }
        }
    }

    fun onDeleteBtnClick() {
        currentKeyword = ""
        _clickDeleteBtn.call()
    }
}