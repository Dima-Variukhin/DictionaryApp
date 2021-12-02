package com.myapp.dictionaryapp.domain

import com.myapp.dictionaryapp.data.WordData
import com.myapp.dictionaryapp.data.WordsRepository

interface WordDetailsInteractor {
    suspend fun getWordData(word: String, position: Int): WordData

    class Base(
        private val repository: WordsRepository
    ) : WordDetailsInteractor {
        override suspend fun getWordData(word: String, position: Int) =
            repository.getWordData(word, position)
    }
}