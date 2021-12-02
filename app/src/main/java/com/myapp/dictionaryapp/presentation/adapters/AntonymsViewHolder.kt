package com.myapp.dictionaryapp.presentation.adapters

import android.view.View
import android.widget.TextView
import com.myapp.dictionaryapp.R
import com.myapp.dictionaryapp.presentation.WordUi

class AntonymsViewHolder(view: View) : WordDetailViewHolder<WordUi.Antonyms>(view) {
    private val textView: TextView = itemView.findViewById(R.id.textView)
    override fun bind(model: Any) {
        val value = (model as WordUi.Antonyms).value
        val data = value.toString().replace("[", "").replace("]", "")
        textView.text = data

    }
}