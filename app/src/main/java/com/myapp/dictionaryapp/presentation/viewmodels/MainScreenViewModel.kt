package com.myapp.dictionaryapp.presentation.viewmodels

import androidx.annotation.IdRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.dictionaryapp.R
import com.myapp.dictionaryapp.di.MainWordDI.getWordInteractor
import com.myapp.dictionaryapp.domain.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {
    var searchState = MutableLiveData<Pair<Int, String?>>()
    var progressState = MutableLiveData<Boolean>()

    private val interactor = getWordInteractor()
    private var job: Job? = null
    private var lastQuery: String = ""

    fun fetch(query: String) {
        viewModelScope.debounceLaunch(300) {
            lastQuery = query
            progressState.postValue(true)
            when (interactor.fetch(query)) {
                Status.NO_RESULTS -> showScreenWithId(R.id.no_results)
                Status.NO_CONNECTION -> showScreenWithId(R.id.no_connection)
                Status.SERVICE_UNAVAILABLE -> showScreenWithId(R.id.service_unavailable)
                Status.SUCCESS -> showScreenWithId(R.id.go_to_search_results)
            }
        }
    }

    fun fetch() = fetch(lastQuery)

    private fun showScreenWithId(@IdRes id: Int) {
        progressState.postValue(false)
        searchState.postValue(Pair(id, lastQuery))
    }

    private fun CoroutineScope.debounceLaunch(
        time: Long,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        job?.cancel()
        return launch {
            delay(time)
            block()
        }.also {
            job = it
        }
    }
}