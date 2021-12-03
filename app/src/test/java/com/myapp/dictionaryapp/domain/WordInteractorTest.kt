package com.myapp.dictionaryapp.domain

import com.myapp.dictionaryapp.core.NetworkConnectionException
import com.myapp.dictionaryapp.core.ServerUnavailableException
import com.myapp.dictionaryapp.data.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test


class WordInteractorTest {
    private companion object {
        const val STUB_WORD = "make"
    }

    private lateinit var interactor: WordInteractor

    @Test
    fun testFetchSuccessWithNoResults() {
        interactor = WordInteractor.Base(EmptyRepository())
        runBlocking {
            val actual = interactor.fetch(STUB_WORD)
            assertThat(actual, `is`(Status.NO_RESULTS))
        }
    }

    @Test
    fun testFetchSuccess() {
        interactor = WordInteractor.Base(OneWordRepository())
        runBlocking {
            val actual = interactor.fetch(STUB_WORD)
            assertThat(actual, `is`(Status.SUCCESS))
        }
    }

    @Test
    fun testNoConnection() {
        interactor = WordInteractor.Base(NetworkExceptionRepository())
        runBlocking {
            val actual = interactor.fetch(STUB_WORD)
            assertThat(actual, `is`(Status.NO_CONNECTION))
        }
    }

    @Test
    fun testServiceUnavailable() {
        interactor = WordInteractor.Base(ServiceUnavailableRepository())
        runBlocking {
            val actual = interactor.fetch(STUB_WORD)
            assertThat(actual, `is`(Status.SERVICE_UNAVAILABLE))
        }
    }
}


class EmptyRepository : WordsRepository {
    override suspend fun getWords(word: String) = emptyList<WordData>()
    override suspend fun getWordData(word: String, id: Int): WordData {
        throw UnsupportedOperationException("no need to use this here")
    }
}


class OneWordRepository : WordsRepository {
    override suspend fun getWords(word: String): List<WordData> = listOf(getWord())
    override suspend fun getWordData(word: String, id: Int): WordData {
        throw UnsupportedOperationException("no need to use this here")
    }


    private fun getWord() = WordData(
        "make",
        "meɪk",
        "Old English macian, of West Germanic origin, from a base meaning ‘fitting’; related to match1.",
        listOf(PhoneticsItemData("meɪk")),
        listOf(
            MeaningsItemData(
                "verb",
                listOf(
                    DefinitionsItemData(
                        "form (something) by putting parts together or combining substances; create.",
                        "my grandmother made a dress for me",
                        listOf(
                            "construct",
                            "build",
                            "assemble",
                            "put together",
                            "manufacture",
                            "produce",
                            "fabricate",
                            "create",
                            "form",
                            "fashion",
                            "model",
                            "mould",
                            "shape",
                            "forge",
                            "bring into existence"
                        ),
                        listOf("destroy")
                    )
                )
            )
        )
    )
}

class NetworkExceptionRepository : WordsRepository {
    override suspend fun getWords(word: String) = throw NetworkConnectionException()

    override suspend fun getWordData(word: String, id: Int): WordData {
        throw UnsupportedOperationException("no need to use this here")
    }
}

class ServiceUnavailableRepository : WordsRepository {
    override suspend fun getWords(word: String) = throw ServerUnavailableException()

    override suspend fun getWordData(word: String, id: Int): WordData {
        throw UnsupportedOperationException("no need to use this here")
    }
}