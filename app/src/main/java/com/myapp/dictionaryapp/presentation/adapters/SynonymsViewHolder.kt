package com.myapp.dictionaryapp.presentation.adapters

import android.view.View
import android.widget.TextView
import com.myapp.dictionaryapp.R
import com.myapp.dictionaryapp.presentation.WordUi

class SynonymsViewHolder(view: View) : WordDetailViewHolder<WordUi.Synonyms>(view) {
    private val textView: TextView = itemView.findViewById(R.id.textView)
    override fun bind(model: Any) {
        val value = (model as WordUi.Synonyms).value
        val data = value.toString().replace("[", "").replace("]", "")
        textView.text = data
    }
}