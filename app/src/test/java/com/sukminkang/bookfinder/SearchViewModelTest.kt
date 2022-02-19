package com.sukminkang.bookfinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import com.sukminkang.bookfinder.ui.component.search.SearchViewModel
import com.sukminkang.bookfinder.ui.component.search.SearchViewModel.KeywordStatus.*
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.rxkotlin.Singles
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

class SearchViewModelTest {

    private lateinit var searchViewModel: SearchViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler {
            Schedulers.trampoline()
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            Schedulers.trampoline()
        }
        searchViewModel = SearchViewModel()
    }

    @Test
    fun testCheckKeyword() {
        searchViewModel.checkKeyword("java kotlin")
        val result1 = searchViewModel.keywordType.value
        assertEquals(result1, NOT_CONTAIN_OPERATOR)

        searchViewModel.checkKeyword("java kotlin sql")
        val result2 = searchViewModel.keywordType.value
        assertEquals(result2, NUMBER_EXCEED)

        searchViewModel.checkKeyword("testtest!!jva")
        val result3 = searchViewModel.keywordType.value
        assertEquals(result3, SINGLE_KEYWORD)

        searchViewModel.checkKeyword("java|kotlin")
        val result4 = searchViewModel.keywordType.value
        assertEquals(result4, CONTAIN_OR_OPERATOR)

        searchViewModel.checkKeyword("java|||kotlin")
        val result5 = searchViewModel.keywordType.value
        assertEquals(result5, TOO_MANY_OPERATOR)

        searchViewModel.checkKeyword("java | kotlin")
        val result6 = searchViewModel.keywordType.value
        assertEquals(result6, CONTAIN_OR_OPERATOR)

        searchViewModel.checkKeyword("java^&")
        val result7 = searchViewModel.keywordType.value
        assertEquals(result7, SINGLE_KEYWORD)

        searchViewModel.checkKeyword("java-")
        val result8 = searchViewModel.keywordType.value
        assertEquals(result8, CONTAIN_NOT_OPERATOR)

        searchViewModel.checkKeyword("java-java")
        val result9 = searchViewModel.searchInitResult.value!!.books
        for (r in result9) {
            assert(!r.title.contains("java"))
        }
    }

}