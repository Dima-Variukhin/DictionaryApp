package com.myapp.dictionaryapp.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.myapp.dictionaryapp.R
import com.myapp.dictionaryapp.presentation.adapters.WordDetailsAdapter
import com.myapp.dictionaryapp.presentation.viewmodels.WordDetailsViewModel

class WordDetailsFragment : BaseFragment(R.layout.fragment_word_details) {

    companion object {
        const val EXTRA_WORD = "extra_word"
        const val EXTRA_POSITION = "extra_position"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val position = arguments?.getInt(EXTRA_POSITION, 0)
        val word = arguments?.getString(EXTRA_WORD, "")
        recyclerView.setHasFixedSize(true)
        val viewModel = activity?.run {
            ViewModelProviders.of(this).get(WordDetailsViewModel::class.java)
        }
        viewModel?.let { model ->
            model.wordData.observe(viewLifecycleOwner) {
                recyclerView.adapter = WordDetailsAdapter(it)
            }
            model.showData(word!!, position)
        }
    }
}