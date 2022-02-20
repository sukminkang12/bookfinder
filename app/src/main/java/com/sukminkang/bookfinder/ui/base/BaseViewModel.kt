package com.sukminkang.bookfinder.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sukminkang.bookfinder.BookFinderStatus
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.IOException

abstract class BaseViewModel : ViewModel() {
    private var disposableBag = CompositeDisposable()
    val status = MutableLiveData<BookFinderStatus>()



    protected fun addDisposableBag(disposable: Disposable) {
        disposableBag.add(disposable)
    }

    override fun onCleared() {
        disposableBag.clear()
        super.onCleared()
    }

    protected fun handleError(throwable:Throwable) {
        when (throwable) {
            is IOException -> {
                status.postValue(BookFinderStatus.NETWORK_ERROR)
            }
            else -> {
                status.postValue(BookFinderStatus.DEFAULT_ERROR)
            }
        }
    }
}