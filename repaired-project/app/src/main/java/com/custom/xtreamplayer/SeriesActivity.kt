package com.custom.xtreamplayer

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class SeriesActivity : AppCompatActivity() {

    private lateinit var listViewEpisodes: ListView
    private val episodeNames = ArrayList<String>()
    private val episodeIds = ArrayList<String>()
    private val episodeExts = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        listViewEpisodes = findViewById(R.id.listViewCategories)
        val seriesId = intent.getStringExtra("SERIES_ID") ?: ""
        val seriesName = intent.getStringExtra("SERIES_NAME") ?: "Series"
        setTitle(seriesName)

        val sharedPrefs = getSharedPreferences("XtreamPrefs", Context.MODE_PRIVATE)
        val serverUrl = sharedPrefs.getString("SERVER_URL", "") ?: ""
        val user = sharedPrefs.getString("USERNAME", "") ?: ""
        val pass = sharedPrefs.getString("PASSWORD", "") ?: ""

        // THIS is the missing click listener that actually plays the episode!
        listViewEpisodes.setOnItemClickListener { _, _, position, _ ->
            if (position < episodeIds.size) {
                val epId = episodeIds[position]
                val epExt = episodeExts[position]
                
                val intent = Intent(this, PlayerActivity::class.java)
                intent.putExtra("STREAM_ID", epId)
                intent.putExtra("STREAM_EXT", epExt)
                intent.putExtra("SECTION_TYPE", "SERIES")
                intent.putExtra("CATEGORY_ID", "SERIES_DIRECT") 
                intent.putExtra("CATEGORY_NAME", episodeNames[position])
                startActivity(intent)
            }
        }

        if (serverUrl.isNotEmpty() && seriesId.isNotEmpty()) {
            fetchSeriesEpisodes(serverUrl, user, pass, seriesId)
        }
    }

    private fun fetchSeriesEpisodes(serverUrl: String, user: String, pass: String, seriesId: String) {
        Toast.makeText(this, "Loading episodes...", Toast.LENGTH_SHORT).show()
        Thread {
            try {
                val cleanServer = serverUrl.trimEnd('/')
                val apiEndpoint = "$cleanServer/player_api.php?username=$user&password=$pass&action=get_series_info&series_id=$seriesId"

                val url = URL(apiEndpoint)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val responseStream = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonObj = JSONObject(responseStream)
                val episodesObj = jsonObj.optJSONObject("episodes")

                episodeNames.clear()
                episodeIds.clear()
                episodeExts.clear()

                if (episodesObj != null) {
                    val seasonKeys = episodesObj.keys()
                    while (seasonKeys.hasNext()) {
                        val seasonNum = seasonKeys.next()
                        val seasonArray = episodesObj.optJSONArray(seasonNum)
                        if (seasonArray != null) {
                            for (i in 0 until seasonArray.length()) {
                                val epObj = seasonArray.getJSONObject(i)
                                val epId = epObj.optString("id", "")
                                val epTitle = epObj.optString("title", "Episode ${i+1}")
                                val epExt = epObj.optString("container_extension", "mp4")
                                
                                episodeNames.add("S$seasonNum - $epTitle")
                                episodeIds.add(epId)
                                episodeExts.add(epExt)
                            }
                        }
                    }
                }

                runOnUiThread {
                    if (episodeNames.isEmpty()) {
                        episodeNames.add("No episodes available")
                    }

                    val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, episodeNames) {
                        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                            val view = super.getView(position, convertView, parent)
                            val textView = view.findViewById<TextView>(android.R.id.text1)
                            textView.setTextColor(Color.WHITE)
                            return view
                        }
                    }
                    listViewEpisodes.adapter = adapter
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }
}