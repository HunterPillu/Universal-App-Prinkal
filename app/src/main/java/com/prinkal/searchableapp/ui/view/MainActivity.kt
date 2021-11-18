package com.prinkal.searchableapp.ui.view


import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.prinkal.searchableapp.R
import com.prinkal.searchableapp.ui.view.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleSearch()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleSearch()
    }

    private fun handleSearch() {
        val intent = intent
        if (Intent.ACTION_SEARCH == intent.action) {

            tvGuide.visibility = View.GONE
            val searchQuery = intent.getStringExtra(SearchManager.QUERY)

            supportFragmentManager.beginTransaction().replace(
                R.id.main_container,
                HomeFragment.newInstance(searchQuery), "home_frag"
            ).addToBackStack(null).commit()

        } else if (Intent.ACTION_VIEW == intent.action) {
            val selectedSuggestionRowId = intent.dataString

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_m).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )
        return super.onCreateOptionsMenu(menu)
    }
}