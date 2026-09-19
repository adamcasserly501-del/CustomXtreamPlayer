package com.custom.xtreamplayer

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class CategoriesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private val categoryNames = ArrayList<String>()
    private val categoryIds = ArrayList<String>()

    private var serverUrl = ""
    private var user = ""
    private var pass = ""
    private var sectionType = "LIVE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        // FIX: Switch from LinearLayoutManager to GridLayoutManager with 6 columns
        recyclerView = findViewById(R.id.listViewCategories)
        recyclerView.layoutManager = GridLayoutManager(this, 6)
        
        sectionType = intent.getStringExtra("type")?.uppercase() ?: "LIVE"
        if (intent.hasExtra("SECTION_TYPE")) {
            sectionType = intent.getStringExtra("SECTION_TYPE") ?: sectionType
        }

        val sharedPrefs = getSharedPreferences("XtreamPrefs", Context.MODE_PRIVATE)
        serverUrl = sharedPrefs.getString("SERVER_URL", "") ?: ""
        user = sharedPrefs.getString("USERNAME", "") ?: ""
        pass = sharedPrefs.getString("PASSWORD", "") ?: ""

        val titleText = findViewById<TextView>(R.id.tvCategoriesTitle)
        titleText?.text = when (sectionType) {
            "VOD" -> "Movie Categories"
            "SERIES" -> "Series Categories"
            else -> "Live TV Categories"
        }

        if (serverUrl.isNotEmpty()) fetchCategories()
    }

    private fun fetchCategories() {
        Toast.makeText(this, "Loading categories...", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val action = when (sectionType) {
                    "VOD" -> "get_vod_categories"
                    "SERIES" -> "get_series_categories"
                    else -> "get_live_categories"
                }

                val cleanServer = serverUrl.trimEnd('/')
                val url = URL("$cleanServer/player_api.php?username=$user&password=$pass&action=$action")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                
                val responseStream = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonArray = JSONArray(responseStream)

                val tempNames = ArrayList<String>()
                val tempIds = ArrayList<String>()

                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val id = obj.optString("category_id", "")
                    if (id.isNotEmpty()) {
                        tempNames.add(obj.optString("category_name", "Unknown"))
                        tempIds.add(id)
                    }
                }

                withContext(Dispatchers.Main) {
                    categoryNames.clear()
                    categoryIds.clear()

                    categoryNames.add("⭐ My Favorites")
                    categoryIds.add("FAVORITES_ID")

                    categoryNames.addAll(tempNames)
                    categoryIds.addAll(tempIds)

                    adapter = CategoryAdapter(categoryNames, categoryIds)
                    recyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@CategoriesActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    inner class CategoryAdapter(
        private val names: List<String>,
        private val ids: List<String>
    ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

        // FIX: Hook up to the ImageView and TextView inside item_card.xml
        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.tvCardTitle)
            val imageView: ImageView = view.findViewById(R.id.ivCardPoster)

            init {
                view.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val intent = Intent(this@CategoriesActivity, PlayerActivity::class.java)
                        intent.putExtra("CATEGORY_ID", ids[position])
                        intent.putExtra("CATEGORY_NAME", names[position])
                        intent.putExtra("SECTION_TYPE", sectionType)
                        startActivity(intent)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // FIX: Inflate the XML layout instead of writing a plain TextView in code
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
            view.setBackgroundResource(R.drawable.dashboard_button_selector)
            view.isClickable = true
            view.isFocusable = true
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = names[position]
            holder.textView.setTextColor(if (position == 0) Color.YELLOW else Color.WHITE)
            
            // Categories don't have images in the API, so we set a default folder icon
            holder.imageView.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        override fun getItemCount() = names.size
    }
}