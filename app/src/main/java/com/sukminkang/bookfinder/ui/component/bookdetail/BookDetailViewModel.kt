package com.sukminkang.bookfinder.ui.component.bookdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sukminkang.bookfinder.data.DataRepository
import com.sukminkang.bookfinder.data.model.BookDetailResponseModel
import com.sukminkang.bookfinder.ui.base.BaseViewModel

class BookDetailViewModel : BaseViewModel() {

    private val _bookDetail = MutableLiveData<BookDetailResponseModel>()
    private val _goToStore = MutableLiveData<String>()

    val bookDetail : LiveData<BookDetailResponseModel> get() = _bookDetail
    val goToStore : LiveData<String> get() = _goToStore

    fun getBookDetail(isbn:String) {
        addDisposableBag(
            DataRepository.getBookDetail(isbn)
                .subscribe(
                    { resp ->
                        if (resp.error == 0) {
                            _bookDetail.postValue(resp)
                        }
                    },
                    {

                    }
                )
        )
    }

    fun onLinkTextClicked(url:String) {
        _goToStore.postValue(url)
    }
}