package com.myapp.dictionaryapp.data

import com.google.gson.annotations.SerializedName
import com.myapp.dictionaryapp.data.cloud.DefinitionsItem
import com.myapp.dictionaryapp.data.cloud.MeaningsItem
import com.myapp.dictionaryapp.data.cloud.PhoneticsItem

data class WordData(
    val word: String,
    val phonetic: String,
    val origin: String,
    val phonetics: List<PhoneticsItemData>,
    val meanings: List<MeaningsItemData>
)

data class PhoneticsItemData(
    val text: String,
)

data class MeaningsItemData(
    val partOfSpeech: String,
    val definitions: List<DefinitionsItemData>
)

data class DefinitionsItemData(
    val definition: String,
    val example: String,
    val synonyms: List<String>,
    val antonyms: List<Any>,
)
