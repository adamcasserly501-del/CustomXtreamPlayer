package com.custom.xtreamplayer

import android.content.Context
import android.content.Intent
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
import coil.load
import kotlinx.coroutines.launch

class VodActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repository: DataRepository

    private var serverUrl = ""
    private var user = ""
    private var pass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_player)

        repository = DataRepository(this)

        recyclerView = findViewById(R.id.recyclerViewStreams)

        // 6-column movie grid
        recyclerView.layoutManager = GridLayoutManager(this, 6)
        recyclerView.itemAnimator = null

        val categoryId =
            intent.getStringExtra("CATEGORY_ID") ?: ""

        val categoryName =
            intent.getStringExtra("CATEGORY_NAME") ?: "Movies"

        val sectionType =
            intent.getStringExtra("SECTION_TYPE")
                ?.uppercase()
                ?: "VOD"

        findViewById<TextView>(R.id.tvPlayerTitle).text = categoryName

        val sharedPrefs =
            getSharedPreferences(
                "XtreamPrefs",
                Context.MODE_PRIVATE
            )

        serverUrl =
            sharedPrefs
                .getString("SERVER_URL", "")
                ?.trimEnd('/')
                ?: ""

        user =
            sharedPrefs
                .getString("USERNAME", "")
                ?: ""

        pass =
            sharedPrefs
                .getString("PASSWORD", "")
                ?: ""

        if (serverUrl.isBlank() ||
            user.isBlank() ||
            pass.isBlank()
        ) {
            Toast.makeText(
                this,
                "Missing server login information",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (categoryId.isBlank()) {
            Toast.makeText(
                this,
                "Missing movie category",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        loadMovies(
            sectionType,
            categoryId,
            categoryName
        )
    }

    private fun loadMovies(
        sectionType: String,
        categoryId: String,
        categoryName: String
    ) {

        Toast.makeText(
            this,
            "Loading movies...",
            Toast.LENGTH_SHORT
        ).show()

        lifecycleScope.launch {

            try {

                // Download this category from Xtream
                // and save it into the local database.
                if (categoryId != "FAVORITES_ID") {

                    repository.syncCategoryStreams(
                        serverUrl = serverUrl,
                        user = user,
                        pass = pass,
                        sectionType = sectionType,
                        categoryId = categoryId,
                        categoryName = categoryName
                    )
                }

                // Read the movies from the database.
                val movies =
                    repository.getChannelsFromDb(
                        sectionType,
                        categoryId
                    )

                if (movies.isEmpty()) {

                    Toast.makeText(
                        this@VodActivity,
                        "No movies found in this category",
                        Toast.LENGTH_LONG
                    ).show()

                    return@launch
                }

                recyclerView.adapter =
                    VodAdapter(movies)

            } catch (e: Exception) {

                Toast.makeText(
                    this@VodActivity,
                    "Error loading movies: ${
                        e.localizedMessage ?: "Unknown error"
                    }",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    inner class VodAdapter(
        private val movies: List<ChannelEntity>
    ) : RecyclerView.Adapter<VodAdapter.MovieViewHolder>() {

        inner class MovieViewHolder(
            view: View
        ) : RecyclerView.ViewHolder(view) {

            val title: TextView =
                view.findViewById(R.id.tvCardTitle)

            val poster: ImageView =
                view.findViewById(R.id.ivCardPoster)

            init {

                view.isFocusable = true
                view.isClickable = true

                view.setOnClickListener {

                    val position =
                        bindingAdapterPosition

                    if (position == RecyclerView.NO_POSITION) {
                        return@setOnClickListener
                    }

                    val movie =
                        movies[position]

                    openMovie(movie)
                }
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MovieViewHolder {

            val view =
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.item_card,
                        parent,
                        false
                    )

            view.setBackgroundResource(
                R.drawable.dashboard_button_selector
            )

            return MovieViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: MovieViewHolder,
            position: Int
        ) {

            val movie =
                movies[position]

            holder.title.text =
                movie.name

            // Always reset the image because RecyclerView
            // reuses its views.
            holder.poster.setImageResource(
                android.R.drawable.ic_menu_gallery
            )

            if (movie.iconUrl.isNotBlank()) {

                holder.poster.load(movie.iconUrl) {

                    crossfade(true)

                    placeholder(
                        android.R.drawable.ic_menu_gallery
                    )

                    error(
                        android.R.drawable.ic_menu_gallery
                    )
                }
            }
        }

        override fun getItemCount(): Int {
            return movies.size
        }
    }

    private fun openMovie(
        movie: ChannelEntity
    ) {

        if (movie.streamId.isBlank()) {

            Toast.makeText(
                this,
                "This movie has no stream ID",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        /*
         * IMPORTANT:
         *
         * We do NOT play the movie directly here.
         *
         * We send it to PlayerActivity.
         *
         * PlayerActivity is responsible for opening
         * the full-screen video player.
         */

        val intent =
            Intent(
                this,
                PlayerActivity::class.java
            ).apply {

                putExtra(
                    "STREAM_ID",
                    movie.streamId
                )

                putExtra(
                    "STREAM_EXT",
                    movie.containerExtension
                        .ifBlank { "mp4" }
                        .trimStart('.')
                )

                putExtra(
                    "SECTION_TYPE",
                    "VOD"
                )

                putExtra(
                    "CATEGORY_ID",
                    movie.categoryId
                )

                putExtra(
                    "CATEGORY_NAME",
                    movie.name
                )

                putExtra(
                    "STREAM_NAME",
                    movie.name
                )

                putExtra(
                    "STREAM_ICON",
                    movie.iconUrl
                )
            }

        try {

            startActivity(intent)

        } catch (e: Exception) {

            Toast.makeText(
                this,
                "Unable to open movie player: ${
                    e.localizedMessage ?: "Unknown error"
                }",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}