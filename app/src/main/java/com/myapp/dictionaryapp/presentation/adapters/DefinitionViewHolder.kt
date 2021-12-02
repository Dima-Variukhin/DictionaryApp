package com.myapp.dictionaryapp.presentation.adapters

import android.view.View
import android.widget.TextView
import com.myapp.dictionaryapp.R
import com.myapp.dictionaryapp.presentation.WordUi

class DefinitionViewHolder(view: View) : WordDetailViewHolder<WordUi.Definition>(view) {
    private val textView: TextView = itemView.findViewById(R.id.textView)
    override fun bind(model: Any) {
        val value = (model as WordUi.Definition).value
        textView.text = value
    }
}