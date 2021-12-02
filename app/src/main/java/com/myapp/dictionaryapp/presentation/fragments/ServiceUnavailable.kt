package com.myapp.dictionaryapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.myapp.dictionaryapp.R
import com.myapp.dictionaryapp.core.Retry

class ServiceUnavailable : BaseFragment(R.layout.fragment_service_unavailable) {
    private var retry: Retry? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        retry = context as Retry
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retryButton = view.findViewById<Button>(R.id.retryButton)
        retryButton.setOnClickListener {
            retry?.tryAgain()
        }
    }

    override fun onDetach() {
        super.onDetach()
        retry = null
    }
}