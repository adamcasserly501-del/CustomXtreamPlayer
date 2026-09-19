package com.custom.xtreamplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tvExpiry = findViewById<TextView>(R.id.tvExpiry)
        val btnLiveTv = findViewById<Button>(R.id.btnLiveTv)
        val btnMovies = findViewById<Button>(R.id.btnMovies)
        val btnSeries = findViewById<Button>(R.id.btnSeries)
        val btnFavorites = findViewById<Button>(R.id.btnFavorites)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // Snaps remote D-pad focus directly to LIVE TV upon opening
        btnLiveTv.requestFocus()

        loadAccountExpiry(tvExpiry)

        // Standardized to pass SECTION_TYPE matching DataRepository & CategoriesActivity
        btnLiveTv.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java).apply {
                putExtra("SECTION_TYPE", "LIVE")
            }
            startActivity(intent)
        }

        btnMovies.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java).apply {
                putExtra("SECTION_TYPE", "VOD")
            }
            startActivity(intent)
        }

        btnSeries.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java).apply {
                putExtra("SECTION_TYPE", "SERIES")
            }
            startActivity(intent)
        }

        btnFavorites.setOnClickListener {
            val intent = Intent(this, PlayerActivity::class.java).apply {
                putExtra("CATEGORY_ID", "FAVORITES_ID")
                putExtra("CATEGORY_NAME", "⭐ My Favorites")
                putExtra("SECTION_TYPE", "LIVE")
            }
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            val prefs = getSharedPreferences("XtreamPrefs", MODE_PRIVATE)
            prefs.edit().clear().apply()
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    private fun loadAccountExpiry(tvExpiry: TextView) {
        val prefs = getSharedPreferences("XtreamPrefs", MODE_PRIVATE)
        val expTimestamp = prefs.getLong("account_expiry", 0L)

        if (expTimestamp > 0) {
            val date = Date(expTimestamp * 1000L)
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            tvExpiry.text = "Expiry: ${format.format(date)}"
        } else {
            tvExpiry.text = "Expiry: Active"
        }
    }
}