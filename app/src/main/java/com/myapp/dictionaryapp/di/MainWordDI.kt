package com.myapp.dictionaryapp.di

import android.app.Application
import com.myapp.dictionaryapp.data.WordsRepository
import com.myapp.dictionaryapp.data.cache.WordsCache
import com.myapp.dictionaryapp.data.cloud.WordCloudToDataMapper
import com.myapp.dictionaryapp.data.cloud.WordService
import com.myapp.dictionaryapp.data.datasource.WordDataStore
import com.myapp.dictionaryapp.domain.SearchResultsInteractor
import com.myapp.dictionaryapp.domain.WordDetailsInteractor
import com.myapp.dictionaryapp.domain.WordInteractor
import java.lang.UnsupportedOperationException

object MainWordDI {
    private lateinit var config: DI.Config
    private lateinit var wordsCache: WordsCache
    private var repository: WordsRepository? = null
    private var wordDetailsInteractor: WordDetailsInteractor? = null
    private var wordInteractor: WordInteractor? = null
    private var searchResultsInteractor: SearchResultsInteractor? = null

    fun initialize(app: Application, configuration: DI.Config = DI.Config.RELEASE) {
        config = configuration
        wordsCache = WordsCache.Base(app)
    }

    fun getWordInteractor(): WordInteractor {
        if (config == DI.Config.RELEASE && wordInteractor == null)
            wordInteractor = makeWordInteractor(getWordRepository())
        return wordInteractor!!
    }

    fun setWordInteractor(interactor: WordInteractor) {
        if (config == DI.Config.TEST) {
            wordInteractor = interactor
        } else {
            throw UnsupportedOperationException("cannot set interactor if not a DI.Config.TEST")
        }
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
                    NetworkDI.connectionManager,
                    NetworkDI.getService(WordService::class.java),
                    wordsCache
                ),
                WordCloudToDataMapper()
            )
        return repository!!
    }
}