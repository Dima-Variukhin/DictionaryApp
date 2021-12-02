package com.myapp.dictionaryapp.domain

import com.myapp.dictionaryapp.core.NetworkConnectionException
import com.myapp.dictionaryapp.data.WordsRepository

interface WordInteractor {
    suspend fun fetch(word: String): Status

    class Base(
        private val repository: WordsRepository,
    ) : WordInteractor {
        override suspend fun fetch(word: String) = try {
            val list = repository.getWords(word)
            if (list.isEmpty())
                Status.NO_RESULTS
            else
                Status.SUCCESS
        } catch (e: Exception) {
            if (e is NetworkConnectionException)
                Status.NO_CONNECTION
            else
                Status.SERVICE_UNAVAILABLE
        }
    }
}

enum class Status {
    NO_RESULTS,
    SERVICE_UNAVAILABLE,
    NO_CONNECTION,
    SUCCESS
}