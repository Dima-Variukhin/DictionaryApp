package com.myapp.dictionaryapp.data.datasource

import com.myapp.dictionaryapp.data.cache.WordsCache

interface WordDataStoreFactory {
    enum class Priority {
        CLOUD,
        CACHE
    }

    fun create(word: String, priority: Priority): WordDataStore

    class Base(
        private val wordsCache: WordsCache,
        private val cacheWordsDataStore: WordDataStore.CacheWordDataStore,
        private val cloudWordDataStore: WordDataStore.CloudWordDataStore
    ) : WordDataStoreFactory {
        override fun create(word: String, priority: Priority) =
            if (priority == Priority.CLOUD || !wordsCache.isCached(word))
                cloudWordDataStore
            else cacheWordsDataStore
    }
}