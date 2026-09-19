package com.custom.xtreamplayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchActivity : AppCompatActivity() {

    private lateinit var spinnerCountries: AutoCompleteTextView
    private lateinit var etSearchQuery: EditText
    private lateinit var btnExecuteSearch: Button
    private lateinit var recyclerViewSearchResults: RecyclerView

    private lateinit var repository: DataRepository

    // Full country list with Unicode flag icons.
    private val countries = listOf(
        "🌍 All Countries",
        "🇦🇫 Afghanistan",
        "🇦🇱 Albania",
        "🇩🇿 Algeria",
        "🇦🇩 Andorra",
        "🇦🇴 Angola",
        "🇦🇬 Antigua and Barbuda",
        "🇦🇷 Argentina",
        "🇦🇲 Armenia",
        "🇦🇺 Australia",
        "🇦🇹 Austria",
        "🇦🇿 Azerbaijan",
        "🇧🇸 Bahamas",
        "🇧🇭 Bahrain",
        "🇧🇩 Bangladesh",
        "🇧🇧 Barbados",
        "🇧🇾 Belarus",
        "🇧🇪 Belgium",
        "🇧🇿 Belize",
        "🇧🇯 Benin",
        "🇧🇹 Bhutan",
        "🇧🇴 Bolivia",
        "🇧🇦 Bosnia and Herzegovina",
        "🇧🇼 Botswana",
        "🇧🇷 Brazil",
        "🇧🇳 Brunei",
        "🇧🇬 Bulgaria",
        "🇧🇫 Burkina Faso",
        "🇧🇮 Burundi",
        "🇨🇻 Cabo Verde",
        "🇰🇭 Cambodia",
        "🇨🇲 Cameroon",
        "🇨🇦 Canada",
        "🇨🇫 Central African Republic",
        "🇹🇩 Chad",
        "🇨🇱 Chile",
        "🇨🇳 China",
        "🇨🇴 Colombia",
        "🇰🇲 Comoros",
        "🇨🇬 Congo",
        "🇨🇷 Costa Rica",
        "🇭🇷 Croatia",
        "🇨🇺 Cuba",
        "🇨🇾 Cyprus",
        "🇨🇿 Czech Republic",
        "🇨🇩 Democratic Republic of the Congo",
        "🇩🇰 Denmark",
        "🇩🇯 Djibouti",
        "🇩🇲 Dominica",
        "🇩🇴 Dominican Republic",
        "🇪🇨 Ecuador",
        "🇪🇬 Egypt",
        "🇸🇻 El Salvador",
        "🇬🇶 Equatorial Guinea",
        "🇪🇷 Eritrea",
        "🇪🇪 Estonia",
        "🇸🇿 Eswatini",
        "🇪🇹 Ethiopia",
        "🇫🇯 Fiji",
        "🇫🇮 Finland",
        "🇫🇷 France",
        "🇬🇦 Gabon",
        "🇬🇲 Gambia",
        "🇬🇪 Georgia",
        "🇩🇪 Germany",
        "🇬🇭 Ghana",
        "🇬🇷 Greece",
        "🇬🇩 Grenada",
        "🇬🇹 Guatemala",
        "🇬🇳 Guinea",
        "🇬🇼 Guinea-Bissau",
        "🇬🇾 Guyana",
        "🇭🇹 Haiti",
        "🇭🇳 Honduras",
        "🇭🇺 Hungary",
        "🇮🇸 Iceland",
        "🇮🇳 India",
        "🇮🇩 Indonesia",
        "🇮🇷 Iran",
        "🇮🇶 Iraq",
        "🇮🇪 Ireland",
        "🇮🇱 Israel",
        "🇮🇹 Italy",
        "🇯🇲 Jamaica",
        "🇯🇵 Japan",
        "🇯🇴 Jordan",
        "🇰🇿 Kazakhstan",
        "🇰🇪 Kenya",
        "🇰🇮 Kiribati",
        "🇰🇼 Kuwait",
        "🇰🇬 Kyrgyzstan",
        "🇱🇦 Laos",
        "🇱🇻 Latvia",
        "🇱🇧 Lebanon",
        "🇱🇸 Lesotho",
        "🇱🇷 Liberia",
        "🇱🇾 Libya",
        "🇱🇮 Liechtenstein",
        "🇱🇹 Lithuania",
        "🇱🇺 Luxembourg",
        "🇲🇬 Madagascar",
        "🇲🇼 Malawi",
        "🇲🇾 Malaysia",
        "🇲🇻 Maldives",
        "🇲🇱 Mali",
        "🇲🇹 Malta",
        "🇲🇭 Marshall Islands",
        "🇲🇷 Mauritania",
        "🇲🇺 Mauritius",
        "🇲🇽 Mexico",
        "🇫🇲 Micronesia",
        "🇲🇩 Moldova",
        "🇲🇨 Monaco",
        "🇲🇳 Mongolia",
        "🇲🇪 Montenegro",
        "🇲🇦 Morocco",
        "🇲🇿 Mozambique",
        "🇲🇲 Myanmar",
        "🇳🇦 Namibia",
        "🇳🇷 Nauru",
        "🇳🇵 Nepal",
        "🇳🇱 Netherlands",
        "🇳🇿 New Zealand",
        "🇳🇮 Nicaragua",
        "🇳🇪 Niger",
        "🇳🇬 Nigeria",
        "🇰🇵 North Korea",
        "🇲🇰 North Macedonia",
        "🇳🇴 Norway",
        "🇴🇲 Oman",
        "🇵🇰 Pakistan",
        "🇵🇼 Palau",
        "🇵🇸 Palestine",
        "🇵🇦 Panama",
        "🇵🇬 Papua New Guinea",
        "🇵🇾 Paraguay",
        "🇵🇪 Peru",
        "🇵🇭 Philippines",
        "🇵🇱 Poland",
        "🇵🇹 Portugal",
        "🇶🇦 Qatar",
        "🇷🇴 Romania",
        "🇷🇺 Russia",
        "🇷🇼 Rwanda",
        "🇰🇳 Saint Kitts and Nevis",
        "🇱🇨 Saint Lucia",
        "🇻🇨 Saint Vincent and the Grenadines",
        "🇼🇸 Samoa",
        "🇸🇲 San Marino",
        "🇸🇹 Sao Tome and Principe",
        "🇸🇦 Saudi Arabia",
        "🇸🇳 Senegal",
        "🇷🇸 Serbia",
        "🇸🇨 Seychelles",
        "🇸🇱 Sierra Leone",
        "🇸🇬 Singapore",
        "🇸🇰 Slovakia",
        "🇸🇮 Slovenia",
        "🇸🇧 Solomon Islands",
        "🇸🇴 Somalia",
        "🇿🇦 South Africa",
        "🇰🇷 South Korea",
        "🇸🇸 South Sudan",
        "🇪🇸 Spain",
        "🇱🇰 Sri Lanka",
        "🇸🇩 Sudan",
        "🇸🇷 Suriname",
        "🇸🇪 Sweden",
        "🇨🇭 Switzerland",
        "🇸🇾 Syria",
        "🇹🇼 Taiwan",
        "🇹🇯 Tajikistan",
        "🇹🇿 Tanzania",
        "🇹🇭 Thailand",
        "🇹🇱 Timor-Leste",
        "🇹🇬 Togo",
        "🇹🇴 Tonga",
        "🇹🇹 Trinidad and Tobago",
        "🇹🇳 Tunisia",
        "🇹🇷 Turkey",
        "🇹🇲 Turkmenistan",
        "🇹🇻 Tuvalu",
        "🇺🇬 Uganda",
        "🇺🇦 Ukraine",
        "🇦🇪 United Arab Emirates",
        "🇬🇧 United Kingdom",
        "🇺🇸 United States",
        "🇺🇾 Uruguay",
        "🇺🇿 Uzbekistan",
        "🇻🇺 Vanuatu",
        "🇻🇦 Vatican City",
        "🇻🇪 Venezuela",
        "🇻🇳 Vietnam",
        "🇾🇪 Yemen",
        "🇿🇲 Zambia",
        "🇿🇼 Zimbabwe"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        spinnerCountries = findViewById(R.id.spinnerCountries)
        etSearchQuery = findViewById(R.id.etSearchQuery)
        btnExecuteSearch = findViewById(R.id.btnExecuteSearch)
        recyclerViewSearchResults = findViewById(R.id.recyclerViewSearchResults)

        repository = DataRepository(this)

        recyclerViewSearchResults.layoutManager = LinearLayoutManager(this)

        val countryAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            countries
        )

        spinnerCountries.setAdapter(countryAdapter)
        spinnerCountries.setText(countries.first(), false)

        spinnerCountries.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                spinnerCountries.showDropDown()
            }
        }

        spinnerCountries.setOnClickListener {
            spinnerCountries.showDropDown()
        }

        btnExecuteSearch.setOnClickListener {
            val selectedCountry = spinnerCountries.text.toString()
                .replaceFirst(Regex("^\\S+\\s"), "")
                .trim()

            val query = etSearchQuery.text.toString().trim()

            performSearch(selectedCountry, query)
        }
    }

    private fun performSearch(country: String, query: String) {
        if (query.isBlank()) {
            Toast.makeText(
                this,
                "Enter a search term",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        lifecycleScope.launch {
            try {
                val results = withContext(Dispatchers.IO) {
                    repository.searchChannels(query)
                }

                recyclerViewSearchResults.adapter = ChannelAdapter(
                    results,
                    onClick = { channel ->
                        playChannel(channel)
                    }
                )

                if (results.isEmpty()) {
                    Toast.makeText(
                        this@SearchActivity,
                        "No results found for \"$query\"",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@SearchActivity,
                    "Search error: ${e.localizedMessage ?: "Unknown error"}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun playChannel(channel: ChannelEntity) {
        val sharedPrefs = getSharedPreferences(
            "XtreamPrefs",
            MODE_PRIVATE
        )

        val serverUrl = sharedPrefs
            .getString("SERVER_URL", "")
            ?.trimEnd('/')
            ?: ""

        val user = sharedPrefs
            .getString("USERNAME", "")
            ?: ""

        val pass = sharedPrefs
            .getString("PASSWORD", "")
            ?: ""

        if (serverUrl.isBlank() || user.isBlank() || pass.isBlank()) {
            Toast.makeText(
                this,
                "Configuration error: Missing server details",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (channel.streamId.isBlank()) {
            Toast.makeText(
                this,
                "This item has no stream ID",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val extension = channel.containerExtension
            .ifBlank {
                when (channel.sectionType.uppercase()) {
                    "LIVE" -> "ts"
                    else -> "mp4"
                }
            }

        val targetUrl = when (channel.sectionType.uppercase()) {
            "VOD" ->
                "$serverUrl/movie/$user/$pass/${channel.streamId}.$extension"

            "SERIES" ->
                "$serverUrl/series/$user/$pass/${channel.streamId}.mp4"

            else ->
                "$serverUrl/live/$user/$pass/${channel.streamId}.$extension"
        }

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(
                Uri.parse(targetUrl),
                "video/*"
            )
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "No external video player found to play stream",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
