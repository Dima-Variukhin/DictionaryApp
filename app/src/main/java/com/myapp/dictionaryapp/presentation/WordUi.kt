package com.myapp.dictionaryapp.presentation

sealed class WordUi<T> {
    abstract val value: T

    data class Word(override val value: String) : WordUi<String>()
    data class Phonetics(override val value: String) : WordUi<String>()
    data class Origin(override val value: String) : WordUi<String>()
    data class PhoneticsItem(override val value: List<String?>) : WordUi<List<String?>>()
    data class PartOfSpeech(override val value: String) : WordUi<String>()
    data class Definition(override val value: String) : WordUi<String>()
    data class Example(override val value: String) : WordUi<String>()
    data class Synonyms(override val value: List<String>) : WordUi<List<String>>()
    data class Antonyms(override val value: List<Any>) : WordUi<List<Any>>()
}