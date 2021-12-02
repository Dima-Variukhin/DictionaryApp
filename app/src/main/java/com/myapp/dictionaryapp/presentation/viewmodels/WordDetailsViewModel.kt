package com.myapp.dictionaryapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.dictionaryapp.di.MainWordDI
import com.myapp.dictionaryapp.presentation.WordUi
import com.myapp.dictionaryapp.presentation.WordUiMapper
import kotlinx.coroutines.launch

class WordDetailsViewModel : ViewModel() {
    val wordData = MutableLiveData<List<WordUi<*>>>()
    private val interactor = MainWordDI.getWordDetailsInteractor()
    private val mapper = WordUiMapper()

    fun showData(word: String, position: Int?) = viewModelScope.launch {
        wordData.value = mapper.map(interactor.getWordData(word, position ?: 0))
    }
}