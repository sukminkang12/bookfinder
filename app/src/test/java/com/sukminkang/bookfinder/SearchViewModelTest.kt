package com.sukminkang.bookfinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sukminkang.bookfinder.data.model.SearchBooksModel
import com.sukminkang.bookfinder.ui.component.search.SearchViewModel
import com.sukminkang.bookfinder.ui.component.search.SearchViewModel.KeywordStatus.*
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
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
    fun testCheckKeyword1() {
        searchViewModel.checkKeyword("java kotlin")
        val result = searchViewModel.keywordType.value
        assertEquals(result, NOT_CONTAIN_OPERATOR)
    }

    @Test
    fun testCheckKeyword2() {
        searchViewModel.checkKeyword("java kotlin sql")
        val result = searchViewModel.keywordType.value
        assertEquals(result, NUMBER_EXCEED)
    }

    @Test
    fun testCheckKeyword3() {
        searchViewModel.checkKeyword("testtest!!jva")
        val result = searchViewModel.keywordType.value
        assertEquals(result, SINGLE_KEYWORD)
    }

    @Test
    fun testCheckKeyword4() {
        searchViewModel.checkKeyword("java|kotlin")
        val result = searchViewModel.keywordType.value
        assertEquals(result, OR_OPERATOR)
    }

    @Test
    fun testCheckKeyword5() {
        searchViewModel.checkKeyword("java|||kotlin")
        val result = searchViewModel.keywordType.value
        assertEquals(result, TOO_MANY_OPERATOR)
    }

    @Test
    fun testCheckKeyword6() {
        searchViewModel.checkKeyword("java | kotlin")
        val result = searchViewModel.keywordType.value
        assertEquals(result, OR_OPERATOR)
    }

    @Test
    fun testCheckKeyword7() {
        searchViewModel.checkKeyword("java^&")
        val result = searchViewModel.keywordType.value
        assertEquals(result, SINGLE_KEYWORD)
    }

    @Test
    fun testCheckKeyword8() {
        searchViewModel.checkKeyword("java-")
        val result = searchViewModel.keywordType.value
        assertEquals(result, NOT_OPERATOR)
        assertEquals(searchViewModel.searchInitResult.value!!.size, 10)
    }

    @Test
    fun testCheckKeyword9() {
        searchViewModel.checkKeyword("java-kotlin")
        val result = searchViewModel.searchInitResult.value!!
        for (r in result) {
            assert(!r.title.contains("kotlin"))
        }
    }

    @Test
    fun testCheckKeyword10() {
        searchViewModel.checkKeyword("mongodb|mongodb")
        val result = searchViewModel.searchInitResult.value!!
        assertEquals(result.size, 10)
    }

    @Test
    fun testCheckKeyword11() {
        //nice 12, java 30 = 42
        searchViewModel.checkKeyword("java|nice")
        val result = searchViewModel.searchInitResult.value!!
        repeat(2) {
            searchViewModel.getNextBookList()
            result.addAll(searchViewModel.searchNextResult.value!!)
        }
        assertEquals(result.size, 42)
    }

    @Test
    fun testCheckKeyword12() {
        searchViewModel.checkKeyword("nice|java")
        val result = searchViewModel.searchInitResult.value!!
        repeat(2) {
            searchViewModel.getNextBookList()
            result.addAll(searchViewModel.searchNextResult.value!!)
        }
        assertEquals(result.size, 42)
    }

    @Test
    fun testCheckKeyword13() {
        searchViewModel.checkKeyword("java|-kotlin")
        val result = searchViewModel.keywordType.value!!
        assertEquals(result, TOO_MANY_OPERATOR)
    }

    @Test
    fun testCheckKeyword14() {
        var keyWord = "android-kotlin"
        searchViewModel.checkKeyword(keyWord)
        val result = searchViewModel.searchInitResult.value!!
        repeat(30) {
            searchViewModel.getNextBookList()
            result.addAll(searchViewModel.searchNextResult.value!!)
        }
        for (r in result) {
            assert(!r.title.contains("kotlin"))
        }
    }
}