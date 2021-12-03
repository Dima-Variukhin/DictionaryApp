package com.myapp.dictionaryapp.data.datasource

import com.myapp.dictionaryapp.data.cache.WordsCache
import com.myapp.dictionaryapp.data.cloud.DefinitionsItem
import com.myapp.dictionaryapp.data.cloud.MeaningsItem
import com.myapp.dictionaryapp.data.cloud.PhoneticsItem
import com.myapp.dictionaryapp.data.cloud.WordCloud
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.lang.IndexOutOfBoundsException

class CacheWordDataStoreTest {

    private companion object {
        const val STUB_WORD = "make"
    }

    private lateinit var cacheWordDataStore: WordDataStore
    private lateinit var wordsCache: WordsCache

    @Before
    fun setUp() {
        wordsCache = mock(WordsCache::class.java)
        cacheWordDataStore = WordDataStore.CacheWordDataStore(wordsCache)
    }

    @Test
    fun testEmptyList() {
        `when`(wordsCache.get(STUB_WORD)).thenReturn(emptyList())
        runBlocking {
            val result = cacheWordDataStore.getWordCloudList(STUB_WORD)
            assertNotNull(result)
            assertThat(result.size, `is`(0))
        }
    }

    @Test
    fun testOneWordList() {
        `when`(wordsCache.get(STUB_WORD)).thenReturn(listOf(getWord()))
        runBlocking {
            val result = cacheWordDataStore.getWordCloudList(STUB_WORD)
            assertNotNull(result)
            assertThat(result.size, `is`(1))
        }
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun testDetailsEmptyList() {
        `when`(wordsCache.get(STUB_WORD)).thenReturn(emptyList())
        runBlocking {
            cacheWordDataStore.getWordDetails(STUB_WORD, 0)
        }
    }

    @Test
    fun testDetailsSingleWordList() {
        `when`(wordsCache.get(STUB_WORD)).thenReturn(listOf(getWord()))
        runBlocking {
            val result = cacheWordDataStore.getWordDetails(STUB_WORD, 0)
            assertNotNull(result)
            assertThat(result.phonetic, `is`("meɪk"))
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