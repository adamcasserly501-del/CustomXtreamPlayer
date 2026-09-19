package com.custom.xtreamplayer

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class VodActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: DataRepository
    
    private var serverUrl = ""
    private var user = ""
    private var pass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player) // Uses your existing layout

        repository = DataRepository(this)
        recyclerView = findViewById(R.id.recyclerViewStreams)
        
        // 6-Column Grid Layout
        recyclerView.layoutManager = GridLayoutManager(this, 6)
        
        val categoryId = intent.getStringExtra("CATEGORY_ID") ?: ""
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Movies"
        val sectionType = intent.getStringExtra("SECTION_TYPE") ?: "VOD"

        findViewById<TextView>(R.id.tvPlayerTitle).text = categoryName

        val sharedPrefs = getSharedPreferences("XtreamPrefs", Context.MODE_PRIVATE)
        serverUrl = sharedPrefs.getString("SERVER_URL", "") ?: ""
        user = sharedPrefs.getString("USERNAME", "") ?: ""
        pass = sharedPrefs.getString("PASSWORD", "") ?: ""

        loadContent(sectionType, categoryId, categoryName)
    }

    private fun loadContent(sectionType: String, categoryId: String, categoryName: String) {
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            repository.syncCategoryStreams(serverUrl, user, pass, sectionType, categoryId, categoryName)
            val items = repository.getChannelsFromDb(sectionType, categoryId)
            
            withContext(Dispatchers.Main) {
                if (items.isEmpty()) Toast.makeText(this@VodActivity, "No items found", Toast.LENGTH_SHORT).show()
                recyclerView.adapter = VodAdapter(items)
            }
        }
    }

    inner class VodAdapter(private val items: List<ChannelEntity>) : RecyclerView.Adapter<VodAdapter.ViewHolder>() {
        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.tvCardTitle)
            val poster: ImageView = view.findViewById(R.id.ivCardPoster)

            init {
                view.setOnClickListener {
                    val item = items[bindingAdapterPosition]
                    val cleanServer = serverUrl.trimEnd('/')
                    val targetUrl = if (item.sectionType == "SERIES") {
                        "$cleanServer/series/$user/$pass/${item.streamId}.mp4"
                    } else {
                        "$cleanServer/movie/$user/$pass/${item.streamId}.${item.containerExtension}"
                    }
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl)).apply { setDataAndType(Uri.parse(targetUrl), "video/*") })
                    } catch (e: Exception) {
                        Toast.makeText(this@VodActivity, "No player installed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            view.setBackgroundColor(android.graphics.Color.parseColor("#222222"))
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.title.text = item.name
            holder.poster.setImageResource(android.R.drawable.ic_menu_gallery)

            if (item.iconUrl.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val connection = URL(item.iconUrl).openConnection() as HttpURLConnection
                        val bitmap = BitmapFactory.decodeStream(connection.inputStream)
                        withContext(Dispatchers.Main) { holder.poster.setImageBitmap(bitmap) }
                    } catch (e: Exception) {}
                }
            }
        }
        override fun getItemCount() = items.size
    }
}