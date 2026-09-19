package com.custom.xtreamplayer

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnTabLiveVod: Button
    private lateinit var btnTabSeries: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        recyclerView = findViewById(R.id.recyclerViewFavorites)
        btnTabLiveVod = findViewById(R.id.btnTabLiveVod)
        btnTabSeries = findViewById(R.id.btnTabSeries)

        recyclerView.layoutManager = GridLayoutManager(this, 4)

        loadLiveVodFavorites()

        btnTabLiveVod.setOnClickListener {
            loadLiveVodFavorites()
        }

        btnTabSeries.setOnClickListener {
            loadSeriesFavorites()
        }
    }

    private fun loadLiveVodFavorites() {
        // Displays Live TV & VOD favorites grid
    }

    private fun loadSeriesFavorites() {
        // Displays TV Series favorites grid
    }
}