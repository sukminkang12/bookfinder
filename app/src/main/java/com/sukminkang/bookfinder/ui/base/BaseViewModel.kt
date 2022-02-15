package com.sukminkang.bookfinder.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel {
    private var disposableBag = CompositeDisposable()

    protected fun addDisposableBag(disposable: Disposable) {
        disposableBag.add(disposable)
    }

    protected fun disposeBag() {
        disposableBag.dispose()
        disposableBag = CompositeDisposable()
    }

    open fun onDestroy() {
        disposableBag.dispose()
    }
}