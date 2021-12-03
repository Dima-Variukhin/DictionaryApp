package com.myapp.dictionaryapp.data.datasource

import com.myapp.dictionaryapp.core.ConnectionManager
import com.myapp.dictionaryapp.core.NetworkConnectionException
import com.myapp.dictionaryapp.core.ServerUnavailableException
import com.myapp.dictionaryapp.data.cache.WordsCache
import com.myapp.dictionaryapp.data.cloud.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import retrofit2.Response

class CloudWordDataStoreTest {
    private companion object {
        const val STUB_WORD = "make"
    }

    private lateinit var cloudWordDataStore: WordDataStore.CloudWordDataStore
    private lateinit var connectionManager: ConnectionManager
    private lateinit var wordService: WordService
    private lateinit var wordCache: WordsCache

    @Before
    fun setUp() {
        connectionManager = mock(ConnectionManager.Base::class.java)
        wordService = mock(WordService::class.java)
        wordCache = mock(WordsCache::class.java)
        cloudWordDataStore = WordDataStore.CloudWordDataStore(
            connectionManager,
            wordService,
            wordCache
        )
    }


    @Test(expected = UnsupportedOperationException::class)
    fun testGetDetailsFromCloud() {
        runBlocking {
            cloudWordDataStore.getWordDetails(STUB_WORD, 0)
        }
    }

    @Test(expected = NetworkConnectionException::class)
    fun testNetwork() {
        `when`(connectionManager.isNetWorkAvailable()).thenReturn(false)
        runBlocking { cloudWordDataStore.getWordCloudList(STUB_WORD) }
    }

    @Test(expected = ServerUnavailableException::class)
    fun testHandlingExceptionWhileGettingData() {
        runBlocking {
            `when`(connectionManager.isNetWorkAvailable()).thenReturn(true)
            doThrow(KotlinNullPointerException::class.java)
                .`when`(wordService).fetchWord(ArgumentMatchers.anyString())
            cloudWordDataStore.getWordCloudList(STUB_WORD)
        }
    }

    @Test(expected = ServerUnavailableException::class)
    fun testErrorResponse() {
        `when`(connectionManager.isNetWorkAvailable()).thenReturn(true)
        runBlocking {
            val response = Response.error<List<WordCloud>>(404, mock(ResponseBody::class.java))
            `when`(wordService.fetchWord(STUB_WORD)).thenReturn(response)
            cloudWordDataStore.getWordCloudList(STUB_WORD)
        }
    }

    @Test(expected = ServerUnavailableException::class)
    fun testNullResponse() {
        `when`(connectionManager.isNetWorkAvailable()).thenReturn(true)
        runBlocking {
            val response = Response.success<List<WordCloud>>(null)
            `when`(wordService.fetchWord(STUB_WORD)).thenReturn(response)
            cloudWordDataStore.getWordCloudList(STUB_WORD)
        }
    }

    @Test
    fun testSuccessfulResponse() {
        `when`(connectionManager.isNetWorkAvailable()).thenReturn(true)
        runBlocking {
            val response = Response.success<List<WordCloud>>(emptyList())
            `when`(wordService.fetchWord(STUB_WORD)).thenReturn(response)
            val result = cloudWordDataStore.getWordCloudList(STUB_WORD)

            assertNotNull(result)
        }
    }

    @Test
    fun testSuccessfulWithWord() {
        `when`(connectionManager.isNetWorkAvailable()).thenReturn(true)
        runBlocking {
            val response = Response.success<List<WordCloud>>(listOf(getWord()))
            `when`(wordService.fetchWord(STUB_WORD)).thenReturn(response)
            val result = cloudWordDataStore.getWordCloudList(STUB_WORD)

            assertNotNull(result)
            assertThat(result.size, `is`(1))
            assertThat(result.first(), `is`(instanceOf(WordCloud::class.java)))
            assertThat(result.first().word, `is`(STUB_WORD))
        }
    }


    private fun getWord() = WordCloud(
        STUB_WORD,
        "meɪk",
        "Old English macian, of West Germanic origin, from a base meaning ‘fitting’; related to match1.",
        listOf(PhoneticsItem("meɪk")),
        listOf(
            MeaningsItem(
                "verb",
                listOf(
                    DefinitionsItem(
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