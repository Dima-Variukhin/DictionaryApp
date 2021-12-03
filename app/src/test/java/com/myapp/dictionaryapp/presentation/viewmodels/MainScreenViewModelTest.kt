package com.myapp.dictionaryapp.presentation.viewmodels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.myapp.dictionaryapp.R
import com.myapp.dictionaryapp.di.DI
import com.myapp.dictionaryapp.di.MainWordDI
import com.myapp.dictionaryapp.domain.Status
import com.myapp.dictionaryapp.domain.WordInteractor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class MainScreenViewModelTest {

    private lateinit var interactor: WordInteractor
    private lateinit var viewModel: MainScreenViewModel

    private companion object {
        const val STUB_WORD = "make"
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        val app = mock(Application::class.java)
        DI.initialize(app, DI.Config.TEST)
        MainWordDI.setWordInteractor(mock(WordInteractor::class.java))
        interactor = MainWordDI.getWordInteractor()

        viewModel = MainScreenViewModel()
        viewModel.searchState = mock(MutableLiveDataPair::class.java)
        viewModel.progressState = mock(MutableLiveDataBoolean::class.java)
    }

    @Test
    fun testNoResults() {
        runBlocking {
            interactor.stub {
                onBlocking { fetch(STUB_WORD) }.doReturn(Status.NO_RESULTS)
            }

            for (i in 0..10)
                viewModel.fetch(STUB_WORD)

            delay(500)

            verify(viewModel.progressState, times(1)).postValue(true)
            verify(viewModel.progressState, times(1)).postValue(false)
            verify(viewModel.searchState, times(1)).postValue(Pair(R.id.no_results, STUB_WORD))
        }
    }

    @Test
    fun testNoConnection() {
        runBlocking {
            interactor.stub {
                onBlocking { fetch(STUB_WORD) }.doReturn(Status.NO_CONNECTION)
            }

            for (i in 0..10)
                viewModel.fetch(STUB_WORD)

            delay(500)

            verify(viewModel.progressState, times(1)).postValue(true)
            verify(viewModel.progressState, times(1)).postValue(false)
            verify(viewModel.searchState, times(1)).postValue(Pair(R.id.no_connection, STUB_WORD))
        }
    }

    @Test
    fun testServiceUnavailable() {
        runBlocking {
            interactor.stub {
                onBlocking { fetch(STUB_WORD) }.doReturn(Status.SERVICE_UNAVAILABLE)
            }

            for (i in 0..10)
                viewModel.fetch(STUB_WORD)

            delay(500)

            verify(viewModel.progressState, times(1)).postValue(true)
            verify(viewModel.progressState, times(1)).postValue(false)
            verify(viewModel.searchState, times(1)).postValue(
                Pair(
                    R.id.service_unavailable,
                    STUB_WORD
                )
            )
        }
    }

    @Test
    fun testSuccess() {
        runBlocking {
            interactor.stub {
                onBlocking { fetch(STUB_WORD) }.doReturn(Status.SUCCESS)
            }

            for (i in 0..10)
                viewModel.fetch(STUB_WORD)

            delay(500)

            verify(viewModel.progressState, times(1)).postValue(true)
            verify(viewModel.progressState, times(1)).postValue(false)
            verify(viewModel.searchState, times(1)).postValue(
                Pair(
                    R.id.go_to_search_results,
                    STUB_WORD
                )
            )
        }
    }
}

open class MutableLiveDataPair : MutableLiveData<Pair<Int, String?>>()
open class MutableLiveDataBoolean : MutableLiveData<Boolean>()
