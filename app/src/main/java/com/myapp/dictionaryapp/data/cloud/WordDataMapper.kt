package com.myapp.dictionaryapp.data.cloud

import com.myapp.dictionaryapp.core.Abstract
import com.myapp.dictionaryapp.data.*

class WordDataMapper : Abstract.Mapper<List<WordCloud>, List<WordData>> {
    override fun map(data: List<WordCloud>): List<WordData> {
        return data.map { map(it) }
    }

    private fun map(data: WordCloud): WordData {
        return WordData(
            data.word,
            data.phonetic,
            data.origin,
            getPhonetics(data),
            getMeanings(data)
        )
    }

    private fun getPhonetics(data: WordCloud): List<PhoneticsItemData> {
        return data.phonetics.map {
            PhoneticsItemData(it.text)
        }
    }

    private fun getMeanings(data: WordCloud): List<MeaningsItemData> {
        return data.meanings.map { meaning ->
            MeaningsItemData(meaning.partOfSpeech, meaning.definitions.map { defetition ->
                DefinitionsItemData(
                    defetition.definition,
                    defetition.example,
                    defetition.synonyms,
                    listOf(defetition.antonyms)
                )
            })
        }
    }
}