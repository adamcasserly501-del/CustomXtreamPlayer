package com.custom.xtreamplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeriesListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var titleView: TextView
    private lateinit var repository: DataRepository

    private var serverUrl = ""
    private var user = ""
    private var pass = ""

    private var categoryId = ""
    private var categoryName = "Series"

    private val seriesList =
        ArrayList<ChannelEntity>()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_series_list
        )

        recyclerView =
            findViewById(
                R.id.recyclerViewSeries
            )

        titleView =
            findViewById(
                R.id.tvSeriesTitle
            )

        repository =
            DataRepository(this)

        // =========================================================
        // GET CATEGORY INFORMATION
        // =========================================================

        categoryId =
            intent
                .getStringExtra(
                    "CATEGORY_ID"
                )
                ?.trim()
                ?: ""

        categoryName =
            intent
                .getStringExtra(
                    "CATEGORY_NAME"
                )
                ?.trim()
                ?.ifBlank {
                    "Series"
                }
                ?: "Series"

        titleView.text =
            categoryName

        title =
            categoryName

        // =========================================================
        // LOAD XTREAM LOGIN
        // =========================================================

        val sharedPrefs =
            getSharedPreferences(
                "XtreamPrefs",
                Context.MODE_PRIVATE
            )

        serverUrl =
            sharedPrefs
                .getString(
                    "SERVER_URL",
                    ""
                )
                ?.trimEnd('/')
                ?: ""

        user =
            sharedPrefs
                .getString(
                    "USERNAME",
                    ""
                )
                ?: ""

        pass =
            sharedPrefs
                .getString(
                    "PASSWORD",
                    ""
                )
                ?: ""

        // =========================================================
        // VALIDATE SERVER
        // =========================================================

        if (
            serverUrl.isBlank() ||
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

        // =========================================================
        // FIRE TV SERIES GRID
        // =========================================================

        recyclerView.layoutManager =
            GridLayoutManager(
                this,
                6
            )

        recyclerView.itemAnimator =
            null

        recyclerView.isFocusable =
            true

        recyclerView.isFocusableInTouchMode =
            true

        // =========================================================
        // LOAD SERIES
        // =========================================================

        loadSeries()
    }

    // =============================================================
    // LOAD SERIES
    // =============================================================

    private fun loadSeries() {

        Toast.makeText(
            this,
            "Loading series...",
            Toast.LENGTH_SHORT
        ).show()

        lifecycleScope.launch {

            try {

                val results =
                    withContext(
                        Dispatchers.IO
                    ) {

                        /*
                         * Favorites are already stored locally.
                         */
                        if (
                            categoryId
                                .uppercase() ==
                            "FAVORITES_ID"
                        ) {

                            repository.getChannelsFromDb(
                                "SERIES",
                                "FAVORITES_ID"
                            )

                        } else {

                            /*
                             * First download the selected
                             * category from Xtream.
                             */
                            repository.syncCategoryStreams(
                                serverUrl,
                                user,
                                pass,
                                "SERIES",
                                categoryId,
                                categoryName
                            )

                            /*
                             * syncCategoryStreams() saves the
                             * results into Room.
                             *
                             * Now retrieve them.
                             */
                            repository.getChannelsFromDb(
                                "SERIES",
                                categoryId
                            )
                        }
                    }

                seriesList.clear()

                seriesList.addAll(
                    results
                )

                // =================================================
                // NO SERIES
                // =================================================

                if (
                    seriesList.isEmpty()
                ) {

                    recyclerView.adapter =
                        EmptySeriesAdapter()

                    Toast.makeText(
                        this@SeriesListActivity,
                        "No series found",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@launch
                }

                // =================================================
                // DISPLAY SERIES
                // =================================================

                recyclerView.adapter =
                    SeriesAdapter(
                        seriesList
                    )

                // =================================================
                // FIRE TV INITIAL FOCUS
                // =================================================

                recyclerView.post {

                    recyclerView.requestFocus()

                    if (
                        recyclerView.childCount > 0
                    ) {

                        recyclerView
                            .getChildAt(0)
                            ?.requestFocus()
                    }
                }

            } catch (
                e: Exception
            ) {

                Toast.makeText(
                    this@SeriesListActivity,
                    "Error loading series: ${
                        e.localizedMessage
                            ?: "Unknown error"
                    }",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // =============================================================
    // SERIES ADAPTER
    // =============================================================

    inner class SeriesAdapter(
        private val items: List<ChannelEntity>
    ) :
        RecyclerView.Adapter<
            SeriesAdapter.SeriesViewHolder
            >() {

        inner class SeriesViewHolder(
            view: View
        ) :
            RecyclerView.ViewHolder(view) {

            val titleView: TextView =
                view.findViewById(
                    R.id.tvCardTitle
                )

            val posterView: ImageView =
                view.findViewById(
                    R.id.ivCardPoster
                )

            init {

                // -------------------------------------------------
                // FIRE TV FOCUS
                // -------------------------------------------------

                itemView.isFocusable =
                    true

                itemView.isFocusableInTouchMode =
                    true

                itemView.isClickable =
                    true

                // -------------------------------------------------
                // OPEN SERIES
                // -------------------------------------------------

                itemView.setOnClickListener {

                    val position =
                        bindingAdapterPosition

                    if (
                        position ==
                        RecyclerView.NO_POSITION
                    ) {

                        return@setOnClickListener
                    }

                    openSeries(
                        items[position]
                    )
                }
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): SeriesViewHolder {

            val view =
                LayoutInflater
                    .from(parent.context)
                    .inflate(
                        R.layout.item_card,
                        parent,
                        false
                    )

            /*
             * Keep your existing Xtreme Player
             * focus/colour selector.
             */
            view.setBackgroundResource(
                R.drawable.dashboard_button_selector
            )

            return SeriesViewHolder(
                view
            )
        }

        override fun onBindViewHolder(
            holder: SeriesViewHolder,
            position: Int
        ) {

            val series =
                items[position]

            holder.titleView.text =
                series.name

            // =====================================================
            // LOAD SERIES POSTER
            // =====================================================

            if (
                series.iconUrl.isNotBlank()
            ) {

                holder.posterView.load(
                    series.iconUrl
                ) {

                    crossfade(true)

                    placeholder(
                        android.R.drawable.ic_menu_gallery
                    )

                    error(
                        android.R.drawable.ic_menu_gallery
                    )
                }

            } else {

                holder.posterView.setImageResource(
                    android.R.drawable.ic_menu_gallery
                )
            }
        }

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

        if (
            series.streamId.isBlank()
        ) {

            Toast.makeText(
                this,
                "This series has no ID",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val intent =
            Intent(
                this,
                SeriesActivity::class.java
            ).apply {

                putExtra(
                    "SERIES_ID",
                    series.streamId
                )

                putExtra(
                    "SERIES_NAME",
                    series.name
                )
            }

        startActivity(intent)
    }

    // =============================================================
    // EMPTY SERIES ADAPTER
    // =============================================================

    inner class EmptySeriesAdapter :
        RecyclerView.Adapter<
            EmptySeriesAdapter.ViewHolder
            >() {

        inner class ViewHolder(
            view: View
        ) :
            RecyclerView.ViewHolder(view) {

            val titleView: TextView =
                view.findViewById(
                    R.id.tvCardTitle
                )

            val posterView: ImageView =
                view.findViewById(
                    R.id.ivCardPoster
                )
        }

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

            return ViewHolder(
                view
            )
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {

            holder.titleView.text =
                "No series available"

            holder.posterView.setImageResource(
                android.R.drawable.ic_menu_gallery
            )
        }

        override fun getItemCount():
                Int {

            return 1
        }
    }

    // =============================================================
    // FIRE TV BACK
    // =============================================================

    override fun dispatchKeyEvent(
        event: KeyEvent
    ): Boolean {

        if (
            event.action ==
            KeyEvent.ACTION_DOWN &&
            event.repeatCount == 0 &&
            event.keyCode ==
            KeyEvent.KEYCODE_BACK
        ) {

            finish()

            return true
        }

        return super.dispatchKeyEvent(
            event
        )
    }
}