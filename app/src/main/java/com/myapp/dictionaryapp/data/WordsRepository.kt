package com.myapp.dictionaryapp.data

import com.myapp.dictionaryapp.data.cache.WordsCache
import com.myapp.dictionaryapp.data.cloud.WordCloud
import com.myapp.dictionaryapp.data.cloud.WordDataMapper
import com.myapp.dictionaryapp.data.datasource.WordDataStore

interface WordsRepository {
    suspend fun getWords(word: String): List<WordData>
    suspend fun getWordData(word: String, id: Int): WordData


    class Base(
        private val wordsCache: WordsCache,
        private val cacheWordsDataStore: WordDataStore.CacheWordDataStore,
        private val cloudWordDataStore: WordDataStore.CloudWordDataStore,
        private val wordDataMapper: WordDataMapper
    ) : WordsRepository {
        override suspend fun getWords(word: String): List<WordData> {
            val list: List<WordCloud> = if (wordsCache.isCached(word)) {
                cacheWordsDataStore.getWordCloudList(word)
            } else {
                cloudWordDataStore.getWordCloudList(word)
            }
            return wordDataMapper.map(list)
        }

        override suspend fun getWordData(word: String, id: Int) = getWords(word)[id]
    }
}