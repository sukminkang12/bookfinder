package com.sukminkang.bookfinder.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private var disposableBag = CompositeDisposable()

    protected fun addDisposableBag(disposable: Disposable) {
        disposableBag.add(disposable)
    }

    override fun onCleared() {
        disposableBag.clear()
        super.onCleared()
    }
}