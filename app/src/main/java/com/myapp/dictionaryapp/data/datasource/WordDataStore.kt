package com.myapp.dictionaryapp.data.datasource

import com.myapp.dictionaryapp.core.ConnectionManager
import com.myapp.dictionaryapp.core.NetworkConnectionException
import com.myapp.dictionaryapp.core.ServerUnavailableException
import com.myapp.dictionaryapp.data.cache.WordsCache
import com.myapp.dictionaryapp.data.cloud.WordCloud
import com.myapp.dictionaryapp.data.cloud.WordService

interface WordDataStore {
    suspend fun getWordCloudList(word: String): List<WordCloud>
    suspend fun getWordDetails(word: String, id: Int): WordCloud

    class CacheWordDataStore(
        private val wordsCache: WordsCache
    ) : WordDataStore {
        override suspend fun getWordCloudList(word: String) = wordsCache.get(word)

        override suspend fun getWordDetails(word: String, id: Int) =
            getWordCloudList(word)[id]
    }

    class CloudWordDataStore(
        private val connectionManager: ConnectionManager,
        private val wordService: WordService,
        private val wordsCache: WordsCache
    ) : WordDataStore {
        override suspend fun getWordCloudList(word: String) =
            if (connectionManager.isNetWorkAvailable()) {
                val wordList: List<WordCloud>
                try {
                    val wordsAsync = wordService.fetchWord(word)
                    wordList = wordsAsync.body()!!
                    wordsCache.put(word, wordList)
                } catch (e: Exception) {
                    throw ServerUnavailableException()
                }
                wordList
            } else {
                throw NetworkConnectionException()
            }

        override suspend fun getWordDetails(word: String, id: Int) =
            throw UnsupportedOperationException("Operation is not available")
    }
}