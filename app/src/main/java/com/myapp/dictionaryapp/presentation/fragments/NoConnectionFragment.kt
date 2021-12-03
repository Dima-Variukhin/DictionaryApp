package com.myapp.dictionaryapp.presentation.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.myapp.dictionaryapp.R
import com.myapp.dictionaryapp.core.Retry

class NoConnectionFragment : BaseFragment(R.layout.fragment_no_connection) {
    private var retry: Retry? = null
    private var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            retry?.tryAgain()
        }
    }

    override fun onStart() {
        super.onStart()
        context?.registerReceiver(receiver, IntentFilter())
    }

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

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(receiver)
    }

    override fun onDetach() {
        super.onDetach()
        retry = null
    }
}