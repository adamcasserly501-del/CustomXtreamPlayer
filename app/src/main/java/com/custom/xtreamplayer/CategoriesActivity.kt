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
import java.net.URLEncoder

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

        recyclerView =
            findViewById(R.id.listViewCategories)

        recyclerView.layoutManager =
            GridLayoutManager(this, 6)

        /*
         * Determine which section opened this screen.
         *
         * LIVE   = Live TV
         * VOD    = Movies
         * SERIES = Series
         */
        sectionType =
            intent.getStringExtra("type")
                ?.uppercase()
                ?: "LIVE"

        if (intent.hasExtra("SECTION_TYPE")) {

            sectionType =
                intent.getStringExtra("SECTION_TYPE")
                    ?.uppercase()
                    ?: sectionType
        }

        /*
         * Load Xtream login details.
         */
        val sharedPrefs =
            getSharedPreferences(
                "XtreamPrefs",
                Context.MODE_PRIVATE
            )

        serverUrl =
            sharedPrefs.getString(
                "SERVER_URL",
                ""
            ) ?: ""

        user =
            sharedPrefs.getString(
                "USERNAME",
                ""
            ) ?: ""

        pass =
            sharedPrefs.getString(
                "PASSWORD",
                ""
            ) ?: ""

        /*
         * Set screen title.
         */
        val titleText =
            findViewById<TextView>(
                R.id.tvCategoriesTitle
            )

        titleText?.text =
            when (sectionType) {

                "VOD" ->
                    "Movie Categories"

                "SERIES" ->
                    "Series Categories"

                else ->
                    "Live TV Categories"
            }

        /*
         * Make sure we have the server.
         */
        if (serverUrl.isBlank()) {

            Toast.makeText(
                this,
                "Missing server configuration",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        fetchCategories()
    }

    // =============================================================
    // FETCH CATEGORIES
    // =============================================================

    private fun fetchCategories() {

        Toast.makeText(
            this,
            "Loading categories...",
            Toast.LENGTH_SHORT
        ).show()

        lifecycleScope.launch(Dispatchers.IO) {

            try {

                /*
                 * Xtream API action depends on the section.
                 */
                val action =
                    when (sectionType) {

                        "VOD" ->
                            "get_vod_categories"

                        "SERIES" ->
                            "get_series_categories"

                        else ->
                            "get_live_categories"
                    }

                val cleanServer =
                    serverUrl.trimEnd('/')

                /*
                 * Encode username and password.
                 */
                val encodedUser =
                    URLEncoder.encode(
                        user,
                        "UTF-8"
                    )

                val encodedPass =
                    URLEncoder.encode(
                        pass,
                        "UTF-8"
                    )

                val apiUrl =
                    "$cleanServer/player_api.php" +
                            "?username=$encodedUser" +
                            "&password=$encodedPass" +
                            "&action=$action"

                val connection =
                    (URL(apiUrl).openConnection()
                            as HttpURLConnection).apply {

                        requestMethod = "GET"
                        connectTimeout = 15_000
                        readTimeout = 30_000
                        useCaches = false
                    }

                try {

                    /*
                     * Check HTTP response.
                     */
                    if (
                        connection.responseCode !in 200..299
                    ) {

                        throw IllegalStateException(
                            "Server returned HTTP ${connection.responseCode}"
                        )
                    }

                    /*
                     * Read API response.
                     */
                    val response =
                        connection.inputStream
                            .bufferedReader()
                            .use {
                                it.readText()
                            }

                    if (response.isBlank()) {

                        throw IllegalStateException(
                            "Server returned an empty response"
                        )
                    }

                    /*
                     * Xtream returns a JSON array
                     * containing the categories.
                     */
                    val jsonArray =
                        JSONArray(response)

                    val tempNames =
                        ArrayList<String>()

                    val tempIds =
                        ArrayList<String>()

                    /*
                     * Extract category names and IDs.
                     */
                    for (
                        i in 0 until jsonArray.length()
                    ) {

                        val obj =
                            jsonArray.optJSONObject(i)
                                ?: continue

                        val id =
                            obj.optString(
                                "category_id",
                                ""
                            ).trim()

                        val name =
                            obj.optString(
                                "category_name",
                                "Unknown"
                            ).trim()

                        if (
                            id.isNotEmpty() &&
                            name.isNotEmpty()
                        ) {

                            tempNames.add(name)
                            tempIds.add(id)
                        }
                    }

                    /*
                     * Update RecyclerView on main thread.
                     */
                    withContext(Dispatchers.Main) {

                        categoryNames.clear()
                        categoryIds.clear()

                        /*
                         * Favorites appears at the top
                         * of every section.
                         */
                        categoryNames.add(
                            "⭐ My Favorites"
                        )

                        categoryIds.add(
                            "FAVORITES_ID"
                        )

                        /*
                         * Add real server categories.
                         */
                        categoryNames.addAll(
                            tempNames
                        )

                        categoryIds.addAll(
                            tempIds
                        )

                        /*
                         * Create adapter.
                         */
                        adapter =
                            CategoryAdapter(
                                categoryNames,
                                categoryIds
                            )

                        recyclerView.adapter =
                            adapter

                        /*
                         * Helpful message if the server
                         * returned no categories.
                         */
                        if (tempNames.isEmpty()) {

                            Toast.makeText(
                                this@CategoriesActivity,
                                "No categories found",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                } finally {

                    connection.disconnect()
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    Toast.makeText(
                        this@CategoriesActivity,
                        "Error loading categories: ${
                            e.localizedMessage
                                ?: "Unknown error"
                        }",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    // =============================================================
    // CATEGORY ADAPTER
    // =============================================================

    inner class CategoryAdapter(
        private val names: List<String>,
        private val ids: List<String>
    ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

        inner class ViewHolder(
            val view: View
        ) : RecyclerView.ViewHolder(view) {

            val textView: TextView =
                view.findViewById(
                    R.id.tvCardTitle
                )

            val imageView: ImageView =
                view.findViewById(
                    R.id.ivCardPoster
                )

            init {

                view.isClickable = true
                view.isFocusable = true
                view.isFocusableInTouchMode = true

                view.setOnClickListener {

                    val position =
                        bindingAdapterPosition

                    if (
                        position ==
                        RecyclerView.NO_POSITION
                    ) {
                        return@setOnClickListener
                    }

                    val categoryId =
                        ids[position]

                    val categoryName =
                        names[position]

                    // =================================================
                    // LIVE TV
                    // =================================================

                    if (sectionType == "LIVE") {

                        val intent =
                            Intent(
                                this@CategoriesActivity,
                                LiveTvActivity::class.java
                            )

                        intent.putExtra(
                            "CATEGORY_ID",
                            categoryId
                        )

                        intent.putExtra(
                            "CATEGORY_NAME",
                            categoryName
                        )

                        intent.putExtra(
                            "SECTION_TYPE",
                            "LIVE"
                        )

                        startActivity(intent)

                        return@setOnClickListener
                    }

                    // =================================================
                    // MOVIES
                    // =================================================

                    if (sectionType == "VOD") {

                        val intent =
                            Intent(
                                this@CategoriesActivity,
                                VodActivity::class.java
                            )

                        intent.putExtra(
                            "CATEGORY_ID",
                            categoryId
                        )

                        intent.putExtra(
                            "CATEGORY_NAME",
                            categoryName
                        )

                        intent.putExtra(
                            "SECTION_TYPE",
                            "VOD"
                        )

                        startActivity(intent)

                        return@setOnClickListener
                    }

                    // =================================================
                    // SERIES
                    // =================================================

                    if (sectionType == "SERIES") {

                        /*
                         * Series now has its own browser screen.
                         *
                         * This keeps Series browsing separate
                         * from PlayerActivity.
                         */
                        val intent =
                            Intent(
                                this@CategoriesActivity,
                                SeriesListActivity::class.java
                            )

                        intent.putExtra(
                            "CATEGORY_ID",
                            categoryId
                        )

                        intent.putExtra(
                            "CATEGORY_NAME",
                            categoryName
                        )

                        intent.putExtra(
                            "SECTION_TYPE",
                            "SERIES"
                        )

                        startActivity(intent)

                        return@setOnClickListener
                    }
                }
            }
        }

        // =========================================================
        // CREATE VIEW HOLDER
        // =========================================================

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {

            val view =
                LayoutInflater
                    .from(parent.context)
                    .inflate(
                        R.layout.item_card,
                        parent,
                        false
                    )

            view.setBackgroundResource(
                R.drawable.dashboard_button_selector
            )

            view.isClickable = true
            view.isFocusable = true
            view.isFocusableInTouchMode = true

            return ViewHolder(view)
        }

        // =========================================================
        // BIND CATEGORY
        // =========================================================

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {

            holder.textView.text =
                names[position]

            /*
             * Make Favorites stand out.
             */
            holder.textView.setTextColor(
                if (position == 0) {
                    Color.YELLOW
                } else {
                    Color.WHITE
                }
            )

            /*
             * Category API does not normally provide
             * poster artwork.
             */
            holder.imageView.setImageResource(
                android.R.drawable.ic_menu_gallery
            )
        }

        override fun getItemCount(): Int =
            names.size
    }
}