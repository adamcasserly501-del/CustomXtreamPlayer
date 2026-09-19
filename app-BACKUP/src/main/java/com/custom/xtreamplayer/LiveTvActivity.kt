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

class LiveTvActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var titleView: TextView
    private lateinit var repository: DataRepository

    private var serverUrl = ""
    private var user = ""
    private var pass = ""
    private var categoryId = ""
    private var categoryName = "Live TV"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        repository = DataRepository(this)
        
        // 6-column grid layout matching Movies and Categories
        recyclerView = findViewById(R.id.recyclerViewStreams)
        recyclerView.layoutManager = GridLayoutManager(this, 6)
        titleView = findViewById(R.id.tvPlayerTitle)

        categoryId = intent.getStringExtra("CATEGORY_ID") ?: ""
        categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Live Channels"
        titleView.text = categoryName

        val sharedPrefs = getSharedPreferences("XtreamPrefs", Context.MODE_PRIVATE)
        serverUrl = sharedPrefs.getString("SERVER_URL", "") ?: ""
        user = sharedPrefs.getString("USERNAME", "") ?: ""
        pass = sharedPrefs.getString("PASSWORD", "") ?: ""

        if (serverUrl.isNotEmpty()) {
            loadLiveChannels()
        } else {
            Toast.makeText(this, "Server credentials missing in settings", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadLiveChannels() {
        Toast.makeText(this, "Loading Live Channels...", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch {
            try {
                // Sync network channels to DB cache unless viewing local favorites
                if (categoryId.isNotEmpty() && categoryId != "FAVORITES_ID") {
                    repository.syncCategoryStreams(serverUrl, user, pass, "LIVE", categoryId, categoryName)
                }

                // Query local Room DB for cached channels
                val channels = repository.getChannelsFromDb("LIVE", categoryId)

                withContext(Dispatchers.Main) {
                    if (channels.isEmpty()) {
                        Toast.makeText(this@LiveTvActivity, "No live channels found", Toast.LENGTH_SHORT).show()
                    }
                    recyclerView.adapter = LiveChannelAdapter(channels)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LiveTvActivity, "Error loading channels: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    inner class LiveChannelAdapter(
        private val channels: List<ChannelEntity>
    ) : RecyclerView.Adapter<LiveChannelAdapter.ViewHolder>() {

        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.tvCardTitle)
            val imageView: ImageView = view.findViewById(R.id.ivCardPoster)

            init {
                view.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val channel = channels[position]
                        val cleanServer = serverUrl.trimEnd('/')
                        // Formulate live stream transport stream URL (.ts)
                        val streamUrl = "$cleanServer/live/$user/$pass/${channel.streamId}.ts"

                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(Uri.parse(streamUrl), "video/*")
                        }
                        try {
                            startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(this@LiveTvActivity, "No video player application found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            view.setBackgroundResource(R.drawable.dashboard_button_selector)
            view.isClickable = true
            view.isFocusable = true
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val channel = channels[position]
            holder.textView.text = channel.name
            val iconUrl = channel.iconUrl

            // Default placeholder image
            holder.imageView.setImageResource(android.R.drawable.ic_menu_gallery)

            // Asynchronous channel icon/logo loader
            if (!iconUrl.isNullOrEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val connection = URL(iconUrl).openConnection() as HttpURLConnection
                        connection.connectTimeout = 5000
                        connection.readTimeout = 5000
                        connection.doInput = true
                        connection.connect()
                        val bitmap = BitmapFactory.decodeStream(connection.inputStream)
                        if (bitmap != null) {
                            withContext(Dispatchers.Main) {
                                holder.imageView.setImageBitmap(bitmap)
                            }
                        }
                    } catch (e: Exception) {
                        // Keeps fallback image icon on failure
                    }
                }
            }
        }

        override fun getItemCount() = channels.size
    }
}