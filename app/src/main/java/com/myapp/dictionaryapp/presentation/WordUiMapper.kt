package com.myapp.dictionaryapp.presentation

import com.myapp.dictionaryapp.core.Abstract
import com.myapp.dictionaryapp.data.WordData

class WordUiMapper : Abstract.Mapper<WordData, List<WordUi<*>>> {
    override fun map(data: WordData) =
        ArrayList<WordUi<*>>().apply {
            var countDefinitions = 1
            add(WordUi.Word(data.word))
            if (data.phonetic?.isNotEmpty() == true) {
                add(WordUi.Phonetics(data.phonetic))
            }
            if (data.origin?.isNotEmpty() == true) {
                add(WordUi.Origin(data.origin))
            }
            val phoneticsItem = data.phonetics.map { it.text }
            if (data.phonetic?.isNotEmpty() == true) {
                add(WordUi.PhoneticsItem(phoneticsItem))
            }
            data.meanings.map { meaning ->
                if (meaning.partOfSpeech?.isNotEmpty() == true) {
                    add(WordUi.PartOfSpeech(meaning.partOfSpeech))
                }
                meaning.definitions.map { definition ->
                    if (definition.definition?.isNotEmpty() == true) {
                        add(WordUi.Word("Definition $countDefinitions"))
                        countDefinitions++
                        add(WordUi.Definition(definition.definition))
                    }
                    if (!definition.example.isNullOrEmpty()) {
                        add(WordUi.Example(definition.example))
                    }
                    add(WordUi.Synonyms(definition.synonyms))
                    add(WordUi.Antonyms(definition.antonyms))
                }
            }
        }
}
