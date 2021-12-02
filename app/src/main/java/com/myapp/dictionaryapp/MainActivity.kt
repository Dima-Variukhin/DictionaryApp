package com.myapp.dictionaryapp

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.appbar.MaterialToolbar
import com.myapp.dictionaryapp.core.Retry
import com.myapp.dictionaryapp.presentation.fragments.SearchResultsFragment
import com.myapp.dictionaryapp.presentation.viewmodels.MainScreenViewModel

class MainActivity : AppCompatActivity(), Retry {
    private lateinit var viewModel: MainScreenViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)

        viewModel = ViewModelProviders.of(this).get(MainScreenViewModel::class.java)
        viewModel.searchState.observe(this) { data ->
            navController.navigate(data.first,
                Bundle().apply { putString(SearchResultsFragment.EXTRA_WORD, data.second) })
        }
        viewModel.progressState.observe(this) { show ->
            progressBar.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.inputType = InputType.TYPE_CLASS_TEXT
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = fetch(query)
            override fun onQueryTextChange(newText: String) = false

            private fun fetch(text: String): Boolean {
                viewModel.fetch(text)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        } else item.let {
            return super.onOptionsItemSelected(it)
        }
    }

    override fun tryAgain() = viewModel.fetch()
}

