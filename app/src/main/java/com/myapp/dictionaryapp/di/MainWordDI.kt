package com.myapp.dictionaryapp.di

import android.app.Application
import com.myapp.dictionaryapp.data.WordsRepository
import com.myapp.dictionaryapp.data.cache.WordsCache
import com.myapp.dictionaryapp.data.cloud.WordDataMapper
import com.myapp.dictionaryapp.data.cloud.WordService
import com.myapp.dictionaryapp.data.datasource.WordDataStore
import com.myapp.dictionaryapp.domain.SearchResultsInteractor
import com.myapp.dictionaryapp.domain.WordDetailsInteractor
import com.myapp.dictionaryapp.domain.WordInteractor

object MainWordDI {
    private lateinit var wordsCache: WordsCache
    private var repository: WordsRepository? = null
    private var wordDetailsInteractor: WordDetailsInteractor? = null
    private var wordInteractor: WordInteractor? = null
    private var searchResultsInteractor: SearchResultsInteractor? = null

    fun initialize(app: Application) {
        wordsCache = WordsCache.Base(app)
    }

    fun getWordInteractorImpl(): WordInteractor {
        if (wordInteractor == null)
            wordInteractor = makeWordInteractor(getWordRepository())
        return wordInteractor!!
    }

    fun getSearchResultsInteractor(): SearchResultsInteractor {
        if (searchResultsInteractor == null)
            searchResultsInteractor = SearchResultsInteractor.Base(getWordRepository())
        return searchResultsInteractor!!
    }

    fun getWordDetailsInteractor(): WordDetailsInteractor {
        if (wordDetailsInteractor == null)
            wordDetailsInteractor = getWordDetailsInteractor(getWordRepository())
        return wordDetailsInteractor!!
    }

    private fun getWordDetailsInteractor(repository: WordsRepository) =
        WordDetailsInteractor.Base(repository)

    private fun makeWordInteractor(repository: WordsRepository) =
        WordInteractor.Base(repository)

    private fun getWordRepository(): WordsRepository {
        if (repository == null)
            repository = WordsRepository.Base(
                wordsCache,
                WordDataStore.CacheWordDataStore(wordsCache),
                WordDataStore.CloudWordDataStore(
                    NetworkDI.getService(WordService::class.java),
                    wordsCache
                ),
                WordDataMapper()
            )
        return repository!!
    }
}