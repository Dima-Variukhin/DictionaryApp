package com.myapp.dictionaryapp.data.cloud

import com.google.gson.annotations.SerializedName

data class WordCloud(
    @SerializedName("word")
    val word: String,
    @SerializedName("phonetic")
    val phonetic: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("phonetics")
    val phonetics: List<PhoneticsItem>,
    @SerializedName("meanings")
    val meanings: List<MeaningsItem>
)

data class PhoneticsItem(
    @SerializedName("text")
    val text: String,
)

data class MeaningsItem(
    @SerializedName("partOfSpeech")
    val partOfSpeech: String,
    @SerializedName("definitions")
    val definitions: List<DefinitionsItem>
)

data class DefinitionsItem(
    @SerializedName("definition")
    val definition: String,
    @SerializedName("example")
    val example: String,
    @SerializedName("synonyms")
    val synonyms: List<String>,
    @SerializedName("antonyms")
    val antonyms: List<Any>,
)