package com.sukminkang.bookfinder.data

import com.sukminkang.bookfinder.data.model.SearchResponseModel
import com.sukminkang.bookfinder.network.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataRepository {

    companion object {
        fun getBookList(query:String, page:Int): Observable<SearchResponseModel> {

            return Observable.just("")
                .subscribeOn(Schedulers.io())
                .switchMap { ApiService.searchApi.getBookList(query, page).toObservable() }
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}