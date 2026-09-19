package com.custom.xtreamplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesActivity : AppCompatActivity() {

    // =============================================================
    // VIEWS
    // =============================================================

    private lateinit var recyclerView: RecyclerView

    private lateinit var btnTabLiveVod: Button

    private lateinit var btnTabSeries: Button

    // =============================================================
    // REPOSITORY
    // =============================================================

    private lateinit var repository: DataRepository

    // =============================================================
    // CURRENT TAB
    // =============================================================

    private var showingSeries = false

    // =============================================================
    // ON CREATE
    // =============================================================

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        setContentView(
            R.layout.activity_favorites
        )

        // ---------------------------------------------------------
        // REPOSITORY
        // ---------------------------------------------------------

        repository =
            DataRepository(this)

        // ---------------------------------------------------------
        // FIND VIEWS
        // ---------------------------------------------------------

        recyclerView =
            findViewById(
                R.id.recyclerViewFavorites
            )

        btnTabLiveVod =
            findViewById(
                R.id.btnTabLiveVod
            )

        btnTabSeries =
            findViewById(
                R.id.btnTabSeries
            )

        // ---------------------------------------------------------
        // FIRE TV GRID
        // ---------------------------------------------------------

        recyclerView.layoutManager =
            GridLayoutManager(
                this,
                4
            )

        recyclerView.itemAnimator =
            null

        recyclerView.isFocusable =
            true

        recyclerView.isFocusableInTouchMode =
            true

        // ---------------------------------------------------------
        // LOAD DEFAULT TAB
        // ---------------------------------------------------------

        loadLiveVodFavorites()

        // ---------------------------------------------------------
        // LIVE / MOVIES TAB
        // ---------------------------------------------------------

        btnTabLiveVod.setOnClickListener {

            showingSeries = false

            loadLiveVodFavorites()
        }

        // ---------------------------------------------------------
        // SERIES TAB
        // ---------------------------------------------------------

        btnTabSeries.setOnClickListener {

            showingSeries = true

            loadSeriesFavorites()
        }
    }

    // =============================================================
    // LOAD LIVE + MOVIE FAVORITES
    // =============================================================

    private fun loadLiveVodFavorites() {

        lifecycleScope.launch {

            try {

                val liveFavorites =
                    withContext(
                        Dispatchers.IO
                    ) {

                        repository.getChannelsFromDb(
                            "LIVE",
                            "FAVORITES_ID"
                        )
                    }

                val movieFavorites =
                    withContext(
                        Dispatchers.IO
                    ) {

                        repository.getChannelsFromDb(
                            "VOD",
                            "FAVORITES_ID"
                        )
                    }

                val combined =
                    ArrayList<ChannelEntity>()

                combined.addAll(
                    liveFavorites
                )

                combined.addAll(
                    movieFavorites
                )

                if (
                    combined.isEmpty()
                ) {

                    recyclerView.adapter =
                        FavoritesAdapter(
                            emptyList(),
                            false
                        )

                    Toast.makeText(
                        this@FavoritesActivity,
                        "No Live TV or Movie favourites",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@launch
                }

                recyclerView.adapter =
                    FavoritesAdapter(
                        combined,
                        false
                    )

                recyclerView.requestFocus()

                recyclerView.scrollToPosition(
                    0
                )

            } catch (
                e: Exception
            ) {

                Toast.makeText(
                    this@FavoritesActivity,
                    "Error loading favourites: ${
                        e.localizedMessage
                            ?: "Unknown error"
                    }",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // =============================================================
    // LOAD SERIES FAVORITES
    // =============================================================

    private fun loadSeriesFavorites() {

        lifecycleScope.launch {

            try {

                val seriesFavorites =
                    withContext(
                        Dispatchers.IO
                    ) {

                        repository.getChannelsFromDb(
                            "SERIES",
                            "FAVORITES_ID"
                        )
                    }

                if (
                    seriesFavorites.isEmpty()
                ) {

                    recyclerView.adapter =
                        FavoritesAdapter(
                            emptyList(),
                            true
                        )

                    Toast.makeText(
                        this@FavoritesActivity,
                        "No favourite series",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@launch
                }

                recyclerView.adapter =
                    FavoritesAdapter(
                        seriesFavorites,
                        true
                    )

                recyclerView.requestFocus()

                recyclerView.scrollToPosition(
                    0
                )

            } catch (
                e: Exception
            ) {

                Toast.makeText(
                    this@FavoritesActivity,
                    "Error loading series favourites: ${
                        e.localizedMessage
                            ?: "Unknown error"
                    }",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // =============================================================
    // FAVORITES ADAPTER
    // =============================================================

    inner class FavoritesAdapter(
        private val items: List<ChannelEntity>,
        private val isSeries: Boolean
    ) :
        RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

        // ---------------------------------------------------------
        // VIEW HOLDER
        // ---------------------------------------------------------

        inner class ViewHolder(
            view: View
        ) :
            RecyclerView.ViewHolder(view) {

            val titleView =
                view.findViewById<TextView>(
                    R.id.tvCardTitle
                )

            val posterView =
                view.findViewById<ImageView>(
                    R.id.ivCardPoster
                )

            init {

                view.isFocusable =
                    true

                view.isFocusableInTouchMode =
                    true

                view.isClickable =
                    true

                // -------------------------------------------------
                // CLICK
                // -------------------------------------------------

                view.setOnClickListener {

                    val position =
                        bindingAdapterPosition

                    if (
                        position ==
                        RecyclerView.NO_POSITION
                    ) {

                        return@setOnClickListener
                    }

                    val item =
                        items[position]

                    if (isSeries) {

                        openSeries(
                            item
                        )

                    } else {

                        openLiveOrMovie(
                            item
                        )
                    }
                }
            }
        }

        // ---------------------------------------------------------
        // CREATE HOLDER
        // ---------------------------------------------------------

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {

            val view =
                LayoutInflater
                    .from(
                        parent.context
                    )
                    .inflate(
                        R.layout.item_card,
                        parent,
                        false
                    )

            view.setBackgroundResource(
                R.drawable.dashboard_button_selector
            )

            return ViewHolder(
                view
            )
        }

        // ---------------------------------------------------------
        // BIND
        // ---------------------------------------------------------

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {

            val item =
                items[position]

            // -----------------------------------------------------
            // TITLE
            // -----------------------------------------------------

            holder.titleView.text =
                item.name

            // -----------------------------------------------------
            // RESET POSTER
            // -----------------------------------------------------

            holder.posterView.setImageResource(
                android.R.drawable.ic_menu_gallery
            )

            // -----------------------------------------------------
            // LOAD POSTER
            // -----------------------------------------------------

            if (
                item.iconUrl.isNotBlank()
            ) {

                holder.posterView.load(
                    item.iconUrl
                ) {

                    crossfade(
                        true
                    )

                    placeholder(
                        android.R.drawable.ic_menu_gallery
                    )

                    error(
                        android.R.drawable.ic_menu_gallery
                    )
                }
            }
        }

        // ---------------------------------------------------------
        // COUNT
        // ---------------------------------------------------------

        override fun getItemCount():
                Int {

            return items.size
        }
    }

    // =============================================================
    // OPEN SERIES
    // =============================================================

    private fun openSeries(
        series: ChannelEntity
    ) {

        val seriesId =
            series.streamId
                .trim()

        if (
            seriesId.isBlank()
        ) {

            Toast.makeText(
                this,
                "This series has no ID",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        /*
         * IMPORTANT:
         *
         * We DO NOT send a Series favourite
         * to PlayerActivity.
         *
         * We send it to SeriesActivity.
         *
         * SeriesActivity then loads:
         *
         * Series information
         * Seasons
         * Episodes
         */

        val intent =
            Intent(
                this,
                SeriesActivity::class.java
            ).apply {

                putExtra(
                    "SERIES_ID",
                    seriesId
                )

                putExtra(
                    "SERIES_NAME",
                    series.name
                )
            }

        try {

            startActivity(
                intent
            )

        } catch (
            e: Exception
        ) {

            Toast.makeText(
                this,
                "Unable to open series: ${
                    e.localizedMessage
                        ?: "Unknown error"
                }",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // =============================================================
    // OPEN LIVE TV / MOVIE
    // =============================================================

    private fun openLiveOrMovie(
        item: ChannelEntity
    ) {

        val streamId =
            item.streamId
                .trim()

        if (
            streamId.isBlank()
        ) {

            Toast.makeText(
                this,
                "This item has no stream ID",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val type =
            item.sectionType
                .trim()
                .uppercase()

        // ---------------------------------------------------------
        // LIVE TV
        // ---------------------------------------------------------

        if (
            type == "LIVE"
        ) {

            val intent =
                Intent(
                    this,
                    PlayerActivity::class.java
                ).apply {

                    putExtra(
                        "STREAM_ID",
                        streamId
                    )

                    putExtra(
                        "STREAM_EXT",
                        item.containerExtension
                            .ifBlank {
                                "ts"
                            }
                            .trimStart('.')
                    )

                    putExtra(
                        "SECTION_TYPE",
                        "LIVE"
                    )

                    putExtra(
                        "CATEGORY_ID",
                        item.categoryId
                    )

                    putExtra(
                        "CATEGORY_NAME",
                        item.name
                    )

                    putExtra(
                        "STREAM_NAME",
                        item.name
                    )

                    putExtra(
                        "STREAM_ICON",
                        item.iconUrl
                    )
                }

            try {

                startActivity(
                    intent
                )

            } catch (
                e: Exception
            ) {

                Toast.makeText(
                    this,
                    "Unable to open channel: ${
                        e.localizedMessage
                            ?: "Unknown error"
                    }",
                    Toast.LENGTH_LONG
                ).show()
            }

            return
        }

        // ---------------------------------------------------------
        // MOVIE
        // ---------------------------------------------------------

        if (
            type == "VOD"
        ) {

            val intent =
                Intent(
                    this,
                    PlayerActivity::class.java
                ).apply {

                    putExtra(
                        "STREAM_ID",
                        streamId
                    )

                    putExtra(
                        "STREAM_EXT",
                        item.containerExtension
                            .ifBlank {
                                "mp4"
                            }
                            .trimStart('.')
                    )

                    putExtra(
                        "SECTION_TYPE",
                        "VOD"
                    )

                    putExtra(
                        "CATEGORY_ID",
                        item.categoryId
                    )

                    putExtra(
                        "CATEGORY_NAME",
                        item.name
                    )

                    putExtra(
                        "STREAM_NAME",
                        item.name
                    )

                    putExtra(
                        "STREAM_ICON",
                        item.iconUrl
                    )
                }

            try {

                startActivity(
                    intent
                )

            } catch (
                e: Exception
            ) {

                Toast.makeText(
                    this,
                    "Unable to open movie: ${
                        e.localizedMessage
                            ?: "Unknown error"
                    }",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // =============================================================
    // FIRE TV BACK
    // =============================================================

    override fun onKeyDown(
        keyCode: Int,
        event: android.view.KeyEvent?
    ): Boolean {

        if (
            keyCode ==
            android.view.KeyEvent.KEYCODE_BACK
        ) {

            finish()

            return true
        }

        return super.onKeyDown(
            keyCode,
            event
        )
    }
}