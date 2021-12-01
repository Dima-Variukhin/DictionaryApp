package com.myapp.dictionaryapp.data.cache

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myapp.dictionaryapp.data.cloud.WordCloud

interface WordsCache {
    fun put(word: String, words: List<WordCloud>)
    fun get(word: String): List<WordCloud>
    fun isCached(word: String): Boolean

    class Base(context: Context) : WordsCache {
        private val sharedPreferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE)
        private val gson = Gson()

        override fun put(word: String, words: List<WordCloud>) {
            val json = gson.toJson(words)
            sharedPreferences.edit().putString(word, json).apply()
        }

        override fun get(word: String): List<WordCloud> {
            val json = sharedPreferences.getString(word, "")
            val type = object : TypeToken<List<WordCloud>>() {}.type
            return Gson().fromJson(json, type)
        }

        override fun isCached(word: String): Boolean {
            val value = sharedPreferences.getString(word, null)
            return value?.isNotEmpty() ?: false
        }
    }
}