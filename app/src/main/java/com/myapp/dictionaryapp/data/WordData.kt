package com.myapp.dictionaryapp.data

data class WordData(
    val word: String,
    val phonetic: String?,
    val origin: String?,
    val phonetics: List<PhoneticsItemData>,
    val meanings: List<MeaningsItemData>
)

data class PhoneticsItemData(
    val text: String?,
)

data class MeaningsItemData(
    val partOfSpeech: String?,
    val definitions: List<DefinitionsItemData>
)

data class DefinitionsItemData(
    val definition: String?,
    val example: String?,
    val synonyms: List<String>,
    val antonyms: List<Any>,
)
