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

class PlayerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var titleView: TextView
    private lateinit var repository: DataRepository
    
    private var serverUrl = ""
    private var user = ""
    private var pass = ""
    private var categoryId = ""
    private var categoryName = ""
    private var sectionType = "LIVE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        repository = DataRepository(this)
        recyclerView = findViewById(R.id.recyclerViewStreams)
        recyclerView.layoutManager = GridLayoutManager(this, 6)
        titleView = findViewById(R.id.tvPlayerTitle)

        categoryId = intent.getStringExtra("CATEGORY_ID") ?: ""
        categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Streams"
        sectionType = intent.getStringExtra("SECTION_TYPE")?.uppercase() ?: "LIVE"

        titleView.text = categoryName

        val sharedPrefs = getSharedPreferences("XtreamPrefs", Context.MODE_PRIVATE)
        serverUrl = sharedPrefs.getString("SERVER_URL", "") ?: ""
        user = sharedPrefs.getString("USERNAME", "") ?: ""
        pass = sharedPrefs.getString("PASSWORD", "") ?: ""

        if (serverUrl.isNotEmpty() && categoryId.isNotEmpty()) {
            loadStreamsAndChannels()
        } else {
            Toast.makeText(this, "Configuration error: Missing server details or category ID", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadStreamsAndChannels() {
        Toast.makeText(this, "Loading content...", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch {
            try {
                // Sync data from network to local database cache if it's not the favorites folder
                if (categoryId != "FAVORITES_ID") {
                    repository.syncCategoryStreams(serverUrl, user, pass, sectionType, categoryId, categoryName)
                }

                // Query the local database cache safely
                val channels = repository.getChannelsFromDb(sectionType, categoryId)

                withContext(Dispatchers.Main) {
                    if (channels.isEmpty()) {
                        Toast.makeText(this@PlayerActivity, "No items found in this category", Toast.LENGTH_SHORT).show()
                    }
                    recyclerView.adapter = StreamAdapter(channels)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@PlayerActivity, "Error loading content: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    inner class StreamAdapter(
        private val channels: List<ChannelEntity>
    ) : RecyclerView.Adapter<StreamAdapter.ViewHolder>() {

        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.tvCardTitle)
            val imageView: ImageView = view.findViewById(R.id.ivCardPoster)

            init {
                view.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val channel = channels[position]
                        val cleanServer = serverUrl.trimEnd('/')
                        
                        // Formulate the appropriate Xtream Codes media playback link
                        val targetUrl = when (channel.sectionType.uppercase()) {
                            "VOD" -> "$cleanServer/movie/$user/$pass/${channel.streamId}.${channel.containerExtension}"
                            "SERIES" -> "$cleanServer/series/$user/$pass/${channel.streamId}.mp4"
                            else -> "$cleanServer/live/$user/$pass/${channel.streamId}.ts"
                        }

                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(Uri.parse(targetUrl), "video/*")
                        }
                        try {
                            startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(this@PlayerActivity, "No external video player found to play stream", Toast.LENGTH_LONG).show()
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
            val imageUrl = channel.iconUrl

            // Reset image placeholder to prevent flickering glitches during view recycling
            holder.imageView.setImageResource(android.R.drawable.ic_menu_gallery)

            // Fetch poster icon asynchronously if available
            if (!imageUrl.isNullOrEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val connection = URL(imageUrl).openConnection() as HttpURLConnection
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
                        // Keep fallback placeholder image if network connection fails
                    }
                }
            }
        }

        override fun getItemCount() = channels.size
    }
}