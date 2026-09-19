package com.custom.xtreamplayer

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private lateinit var spinnerCountries: Spinner
    private lateinit var etSearchQuery: EditText
    private lateinit var btnExecuteSearch: Button
    private lateinit var recyclerViewSearchResults: RecyclerView

    // Sample countries list - you can expand or load dynamically from your API categories
    private val countries = listOf("All Countries", "USA", "UK", "Canada", "France", "Germany", "Spain", "Arabic")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search) // Ensure you have this layout file

        spinnerCountries = findViewById(R.id.spinnerCountries)
        etSearchQuery = findViewById(R.id.etSearchQuery)
        btnExecuteSearch = findViewById(R.id.btnExecuteSearch)
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults)

        recyclerViewSearchResults.layoutManager = LinearLayoutManager(this)

        // Set up the country selector spinner
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        spinnerCountries.adapter = spinnerAdapter

        btnExecuteSearch.setOnClickListener {
            val selectedCountry = spinnerCountries.selectedItem.toString()
            val query = etSearchQuery.text.toString().trim()

            performSearch(selectedCountry, query)
        }
    }

    private fun performSearch(country: String, query: String) {
        // Implement your filtered search query here, factoring in the selected country filter
    }
}