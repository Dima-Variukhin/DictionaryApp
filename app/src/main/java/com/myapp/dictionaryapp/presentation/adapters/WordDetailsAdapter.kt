package com.myapp.dictionaryapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.myapp.dictionaryapp.R
import com.myapp.dictionaryapp.presentation.WordUi

class WordDetailsAdapter(
    private val items: List<WordUi<*>>,
) :
    RecyclerView.Adapter<WordDetailViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ViewType.WORD.ordinal -> WordViewHolder(parent.makeView(R.layout.title))
        ViewType.PHONETICS.ordinal -> PhoneticsViewHolder(parent.makeView(R.layout.phonetic))
        ViewType.ORIGIN.ordinal -> OriginViewHolder(parent.makeView(R.layout.origin))
        ViewType.PHONETICS_ITEM.ordinal -> PhoneticsItemViewHolder(parent.makeView(R.layout.item))
        ViewType.PART_OF_SPEECH.ordinal -> PartOfSpeechViewHolder(parent.makeView(R.layout.part_of_speech))
        ViewType.DEFINITION.ordinal -> DefinitionViewHolder(parent.makeView(R.layout.defenition_text))
        ViewType.EXAMPLE.ordinal -> ExampleViewHolder(parent.makeView(R.layout.example))
        ViewType.SYNONYMS.ordinal -> SynonymsViewHolder(parent.makeView(R.layout.synonyms))
        ViewType.ANTONYMS.ordinal -> AntonymsViewHolder(parent.makeView(R.layout.antonyms))
        else -> throw UnsupportedOperationException("unknown type of item")
    }

    override fun getItemViewType(position: Int) = when (items[position]) {
        is WordUi.Word -> ViewType.WORD.ordinal
        is WordUi.Phonetics -> ViewType.PHONETICS.ordinal
        is WordUi.Origin -> ViewType.ORIGIN.ordinal
        is WordUi.PhoneticsItem -> ViewType.PHONETICS_ITEM.ordinal
        is WordUi.PartOfSpeech -> ViewType.PART_OF_SPEECH.ordinal
        is WordUi.Definition -> ViewType.DEFINITION.ordinal
        is WordUi.Example -> ViewType.EXAMPLE.ordinal
        is WordUi.Synonyms -> ViewType.SYNONYMS.ordinal
        is WordUi.Antonyms -> ViewType.ANTONYMS.ordinal

    }

    override fun onBindViewHolder(holder: WordDetailViewHolder<*>, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}

abstract class WordDetailViewHolder<T : WordUi<*>>(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(model: Any)
}

private enum class ViewType {
    WORD,
    PHONETICS,
    ORIGIN,
    PHONETICS_ITEM,
    PART_OF_SPEECH,
    DEFINITION,
    EXAMPLE,
    SYNONYMS,
    ANTONYMS,
}

fun ViewGroup.makeView(@LayoutRes layoutResId: Int): View =
    LayoutInflater.from(this.context).inflate(layoutResId, this, false)