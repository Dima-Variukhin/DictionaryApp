package com.myapp.dictionaryapp.data

import com.myapp.dictionaryapp.data.cloud.WordDataMapper
import com.myapp.dictionaryapp.data.datasource.WordDataStoreFactory

interface WordsRepository {
    suspend fun getWords(word: String, reload: Boolean = false): List<WordData>
    suspend fun getWordData(word: String, id: Int): WordData

    class Base(
        private val wordDataStoreFactory: WordDataStoreFactory,
        private val wordDataMapper: WordDataMapper
    ) : WordsRepository {
        override suspend fun getWords(word: String, reload: Boolean): List<WordData> {
            val priority =
                if (reload) WordDataStoreFactory.Priority.CLOUD else WordDataStoreFactory.Priority.CACHE
            val dataStore = wordDataStoreFactory.create(word, priority)
            return wordDataMapper.map(dataStore.getWordCloudList(word))
        }

        override suspend fun getWordData(word: String, id: Int) = getWords(word)[id]
    }
}