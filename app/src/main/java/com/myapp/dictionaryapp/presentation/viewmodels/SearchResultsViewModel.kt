package com.myapp.dictionaryapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.dictionaryapp.di.MainWordDI.getSearchResultsInteractor
import kotlinx.coroutines.launch

class SearchResultsViewModel : ViewModel() {
    val results = MutableLiveData<List<String>>()

    private val interactor = getSearchResultsInteractor()
    fun showResults(word: String) = viewModelScope.launch {
        results.value =
            interactor.getSearchResults(word)
                .map { setText(it.word, it.origin) }
    }

    private fun setText(word: String, origin: String?): String {
        return if (origin?.isNotEmpty() == true) {
            word + "\n\n" + origin.substring(0, origin.indexOf(" ", 15))
        } else word
    }
}