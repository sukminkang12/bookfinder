package com.sukminkang.bookfinder.ui.component.detailbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sukminkang.bookfinder.BookFinderStatus
import com.sukminkang.bookfinder.data.DataRepository
import com.sukminkang.bookfinder.data.model.DetailBookResponseModel
import com.sukminkang.bookfinder.ui.base.BaseViewModel

class DetailBookViewModel : BaseViewModel() {

    private val _bookDetail = MutableLiveData<DetailBookResponseModel>()
    private val _goToStore = MutableLiveData<String>()

    val detailBook : LiveData<DetailBookResponseModel> get() = _bookDetail
    val goToStore : LiveData<String> get() = _goToStore

    fun getBookDetail(isbn:String) {
        addDisposableBag(
            DataRepository.getBookDetail(isbn)
                .subscribe(
                    { resp ->
                        if (resp.error == 0) {
                            _bookDetail.postValue(resp)
                        } else {
                            status.postValue(BookFinderStatus.DEFAULT_ERROR)
                        }
                    },
                    {
                        status.postValue(BookFinderStatus.DEFAULT_ERROR)
                    }
                )
        )
    }

    fun onLinkTextClicked(url:String) {
        _goToStore.postValue(url)
    }
}