package com.myapp.dictionaryapp.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.myapp.dictionaryapp.R
import com.myapp.dictionaryapp.presentation.viewmodels.SearchResultsViewModel

class SearchResultsFragment : BaseFragment(R.layout.fragment_search_results) {
    companion object {
        const val EXTRA_WORD = "extra_word"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchResultsListView: ListView = view.findViewById(R.id.searchResultsListView)
        val viewModel = activity?.run {
            ViewModelProviders.of(this).get(SearchResultsViewModel::class.java)
        }
        viewModel?.let { model ->
            model.results.observe(viewLifecycleOwner) {
                searchResultsListView.apply {
                    adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_selectable_list_item,
                        it
                    )
                    onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        Navigation.findNavController((view))
                            .navigate(R.id.details_screen, Bundle().apply {
                                putInt(WordDetailsFragment.EXTRA_POSITION, position)
                                putString(
                                    WordDetailsFragment.EXTRA_WORD, arguments?.getString(
                                        EXTRA_WORD
                                    )
                                )
                            })
                    }
                }
            }
            model.showResults((arguments?.getString(EXTRA_WORD) ?: ""))
        }
    }
}