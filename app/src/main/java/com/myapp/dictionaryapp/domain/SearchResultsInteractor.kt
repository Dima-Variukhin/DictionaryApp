package com.myapp.dictionaryapp.domain

import com.myapp.dictionaryapp.data.WordData
import com.myapp.dictionaryapp.data.WordsRepository

interface SearchResultsInteractor {
    suspend fun getSearchResults(word: String): List<WordData>

    class Base(
        private val wordsRepository: WordsRepository
    ) : SearchResultsInteractor {
        override suspend fun getSearchResults(word: String) = wordsRepository.getWords(word)
    }
}