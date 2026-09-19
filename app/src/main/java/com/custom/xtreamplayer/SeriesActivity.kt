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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class SeriesActivity : AppCompatActivity() {

    // =============================================================
    // EPISODE RECYCLER VIEW
    // =============================================================

    private lateinit var recyclerViewEpisodes: RecyclerView

    // =============================================================
    // EPISODE DATA
    // =============================================================

    private val episodeNames =
        ArrayList<String>()

    private val episodeIds =
        ArrayList<String>()

    private val episodeExts =
        ArrayList<String>()

    // =============================================================
    // SERIES INFORMATION
    // =============================================================

    private var seriesId = ""

    private var seriesName = ""

    // =============================================================
    // XTREAM LOGIN
    // =============================================================

    private var serverUrl = ""

    private var user = ""

    private var pass = ""

    // =============================================================
    // ON CREATE
    // =============================================================

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        /*
         * We are using the existing categories layout.
         *
         * IMPORTANT:
         *
         * listViewCategories is actually being used
         * as a RecyclerView in this project.
         */
        setContentView(
            R.layout.activity_categories
        )

        // ---------------------------------------------------------
        // FIND EPISODE RECYCLER VIEW
        // ---------------------------------------------------------

        recyclerViewEpisodes =
            findViewById(
                R.id.listViewCategories
            )

        // ---------------------------------------------------------
        // CONFIGURE RECYCLER VIEW
        // ---------------------------------------------------------

        /*
         * One column makes episodes easy to navigate
         * with the Fire TV remote.
         */
        recyclerViewEpisodes.layoutManager =
            GridLayoutManager(
                this,
                1
            )

        recyclerViewEpisodes.itemAnimator =
            null

        recyclerViewEpisodes.isFocusable =
            true

        recyclerViewEpisodes.isFocusableInTouchMode =
            true

        // ---------------------------------------------------------
        // GET SERIES ID
        // ---------------------------------------------------------

        seriesId =
            intent
                .getStringExtra(
                    "SERIES_ID"
                )
                ?.trim()
                ?: ""

        // ---------------------------------------------------------
        // GET SERIES NAME
        // ---------------------------------------------------------

        seriesName =
            intent
                .getStringExtra(
                    "SERIES_NAME"
                )
                ?.trim()
                ?: "Series"

        // ---------------------------------------------------------
        // SET TITLE
        // ---------------------------------------------------------

        val titleView =
            findViewById<TextView>(
                R.id.tvCategoriesTitle
            )

        titleView?.text =
            if (
                seriesName.isNotBlank()
            ) {

                seriesName

            } else {

                "Episodes"
            }

        title =
            if (
                seriesName.isNotBlank()
            ) {

                seriesName

            } else {

                "Episodes"
            }

        // ---------------------------------------------------------
        // LOAD XTREAM LOGIN
        // ---------------------------------------------------------

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

        // ---------------------------------------------------------
        // VALIDATE LOGIN
        // ---------------------------------------------------------

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

        // ---------------------------------------------------------
        // VALIDATE SERIES ID
        // ---------------------------------------------------------

        if (
            seriesId.isBlank()
        ) {

            Toast.makeText(
                this,
                "Missing series ID",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        // ---------------------------------------------------------
        // LOAD EPISODES
        // ---------------------------------------------------------

        fetchSeriesEpisodes()
    }

    // =============================================================
    // FETCH SERIES EPISODES
    // =============================================================

    private fun fetchSeriesEpisodes() {

        Toast.makeText(
            this,
            "Loading episodes...",
            Toast.LENGTH_SHORT
        ).show()

        episodeNames.clear()
        episodeIds.clear()
        episodeExts.clear()

        lifecycleScope.launch {

            try {

                val seriesInfo =
                    withContext(
                        Dispatchers.IO
                    ) {

                        requestSeriesInfo()
                    }

                displayEpisodes(
                    seriesInfo
                )

            } catch (
                e: Exception
            ) {

                Toast.makeText(
                    this@SeriesActivity,
                    "Error loading episodes: ${
                        e.localizedMessage
                            ?: "Unknown error"
                    }",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // =============================================================
    // REQUEST SERIES INFORMATION
    // =============================================================

    private fun requestSeriesInfo():
            JSONObject {

        val cleanServer =
            serverUrl.trimEnd('/')

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

        val encodedSeriesId =
            URLEncoder.encode(
                seriesId,
                "UTF-8"
            )

        val apiUrl =
            "$cleanServer/player_api.php" +
                    "?username=$encodedUser" +
                    "&password=$encodedPass" +
                    "&action=get_series_info" +
                    "&series_id=$encodedSeriesId"

        val connection =
            (
                URL(apiUrl).openConnection()
                    as HttpURLConnection
            ).apply {

                requestMethod =
                    "GET"

                connectTimeout =
                    15_000

                readTimeout =
                    30_000

                useCaches =
                    false

                doInput =
                    true

                setRequestProperty(
                    "User-Agent",
                    "XtreamPlayer"
                )
            }

        try {

            if (
                connection.responseCode !in
                200..299
            ) {

                throw IllegalStateException(
                    "Server returned HTTP ${
                        connection.responseCode
                    }"
                )
            }

            val response =
                connection.inputStream
                    .bufferedReader()
                    .use {
                        it.readText()
                    }

            if (
                response.isBlank()
            ) {

                throw IllegalStateException(
                    "Server returned an empty response"
                )
            }

            return try {

                JSONObject(
                    response
                )

            } catch (
                e: Exception
            ) {

                throw IllegalStateException(
                    "Invalid series response",
                    e
                )
            }

        } finally {

            connection.disconnect()
        }
    }

    // =============================================================
    // DISPLAY EPISODES
    // =============================================================

    private fun displayEpisodes(
        jsonObject: JSONObject
    ) {

        episodeNames.clear()
        episodeIds.clear()
        episodeExts.clear()

        val episodesObject =
            jsonObject.optJSONObject(
                "episodes"
            )

        if (
            episodesObject == null
        ) {

            showNoEpisodes()

            return
        }

        // ---------------------------------------------------------
        // GET SEASON KEYS
        // ---------------------------------------------------------

        val seasonKeys =
            episodesObject.keys()

        val seasons =
            ArrayList<String>()

        while (
            seasonKeys.hasNext()
        ) {

            seasons.add(
                seasonKeys.next()
            )
        }

        // ---------------------------------------------------------
        // SORT SEASONS
        // ---------------------------------------------------------

        seasons.sortWith(
            compareBy(
                {
                    it.toIntOrNull()
                        ?: Int.MAX_VALUE
                },
                {
                    it
                }
            )
        )

        // ---------------------------------------------------------
        // PROCESS EACH SEASON
        // ---------------------------------------------------------

        for (
            seasonNumber in seasons
        ) {

            val seasonArray =
                episodesObject
                    .optJSONArray(
                        seasonNumber
                    )
                    ?: continue

            addSeasonEpisodes(
                seasonNumber,
                seasonArray
            )
        }

        // ---------------------------------------------------------
        // CHECK RESULT
        // ---------------------------------------------------------

        if (
            episodeNames.isEmpty()
        ) {

            showNoEpisodes()

            return
        }

        // ---------------------------------------------------------
        // DISPLAY
        // ---------------------------------------------------------

        recyclerViewEpisodes.adapter =
            EpisodeAdapter(
                episodeNames
            )

        recyclerViewEpisodes.requestFocus()

        recyclerViewEpisodes.scrollToPosition(
            0
        )
    }

    // =============================================================
    // ADD SEASON EPISODES
    // =============================================================

    private fun addSeasonEpisodes(
        seasonNumber: String,
        seasonArray: JSONArray
    ) {

        for (
            i in 0 until seasonArray.length()
        ) {

            val episode =
                seasonArray.optJSONObject(
                    i
                )
                    ?: continue

            // -----------------------------------------------------
            // EPISODE ID
            // -----------------------------------------------------

            val episodeId =
                firstNonEmpty(
                    episode.optString(
                        "id",
                        ""
                    ),
                    episode.optString(
                        "episode_id",
                        ""
                    ),
                    episode.optString(
                        "stream_id",
                        ""
                    )
                )

            if (
                episodeId.isBlank()
            ) {

                continue
            }

            // -----------------------------------------------------
            // EPISODE TITLE
            // -----------------------------------------------------

            val episodeTitle =
                firstNonEmpty(
                    episode.optString(
                        "title",
                        ""
                    ),
                    episode.optString(
                        "name",
                        ""
                    ),
                    "Episode ${i + 1}"
                )

            // -----------------------------------------------------
            // EPISODE NUMBER
            // -----------------------------------------------------

            val episodeNumber =
                firstNonEmpty(
                    episode.optString(
                        "episode_num",
                        ""
                    ),
                    episode.optString(
                        "episode_number",
                        ""
                    )
                )

            // -----------------------------------------------------
            // EPISODE EXTENSION
            // -----------------------------------------------------

            val episodeExtension =
                firstNonEmpty(
                    episode.optString(
                        "container_extension",
                        ""
                    ),
                    episode.optString(
                        "extension",
                        ""
                    ),
                    "mp4"
                )
                    .trim()
                    .trimStart('.')
                    .lowercase()

            // -----------------------------------------------------
            // DISPLAY NAME
            // -----------------------------------------------------

            val displayName =
                if (
                    episodeNumber.isNotBlank()
                ) {

                    "S$seasonNumber E$episodeNumber - $episodeTitle"

                } else {

                    "S$seasonNumber - $episodeTitle"
                }

            // -----------------------------------------------------
            // SAVE DATA
            // -----------------------------------------------------

            episodeNames.add(
                displayName
            )

            episodeIds.add(
                episodeId
            )

            episodeExts.add(
                episodeExtension.ifBlank {
                    "mp4"
                }
            )
        }
    }

    // =============================================================
    // EPISODE ADAPTER
    // =============================================================

    inner class EpisodeAdapter(
        private val names: List<String>
    ) :
        RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

        inner class EpisodeViewHolder(
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

                // -------------------------------------------------
                // FIRE TV FOCUS
                // -------------------------------------------------

                view.isFocusable =
                    true

                view.isFocusableInTouchMode =
                    true

                view.isClickable =
                    true

                // -------------------------------------------------
                // CLICK EPISODE
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

                    openEpisode(
                        position
                    )
                }
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): EpisodeViewHolder {

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

            return EpisodeViewHolder(
                view
            )
        }

        override fun onBindViewHolder(
            holder: EpisodeViewHolder,
            position: Int
        ) {

            holder.titleView.text =
                names[position]

            /*
             * Episodes normally don't need their
             * own poster.
             */
            holder.posterView.setImageResource(
                android.R.drawable.ic_menu_gallery
            )
        }

        override fun getItemCount():
                Int {

            return names.size
        }
    }

    // =============================================================
    // OPEN EPISODE
    // =============================================================

    private fun openEpisode(
        position: Int
    ) {

        // ---------------------------------------------------------
        // VALIDATE POSITION
        // ---------------------------------------------------------

        if (
            position < 0 ||
            position >= episodeIds.size
        ) {

            return
        }

        // ---------------------------------------------------------
        // GET EPISODE DATA
        // ---------------------------------------------------------

        val episodeId =
            episodeIds[position]
                .trim()

        val episodeExtension =
            if (
                position <
                episodeExts.size
            ) {

                episodeExts[position]
                    .trim()
                    .trimStart('.')
                    .lowercase()

            } else {

                "mp4"
            }

        val episodeName =
            if (
                position <
                episodeNames.size
            ) {

                episodeNames[position]

            } else {

                "Episode"
            }

        // ---------------------------------------------------------
        // VALIDATE EPISODE ID
        // ---------------------------------------------------------

        if (
            episodeId.isBlank()
        ) {

            Toast.makeText(
                this,
                "This episode has no ID",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // ---------------------------------------------------------
        // CREATE PLAYER INTENT
        // ---------------------------------------------------------

        val playerIntent =
            Intent(
                this,
                PlayerActivity::class.java
            ).apply {

                /*
                 * IMPORTANT:
                 *
                 * For a Series episode, this is the
                 * episode ID returned by get_series_info.
                 */
                putExtra(
                    "STREAM_ID",
                    episodeId
                )

                /*
                 * Use the actual extension returned
                 * by the provider.
                 */
                putExtra(
                    "STREAM_EXT",
                    episodeExtension.ifBlank {
                        "mp4"
                    }
                )

                /*
                 * PlayerActivity uses this to construct:
                 *
                 * /series/user/pass/episodeId.extension
                 */
                putExtra(
                    "SECTION_TYPE",
                    "SERIES"
                )

                /*
                 * This prevents PlayerActivity from
                 * treating the episode as a Series category.
                 */
                putExtra(
                    "CATEGORY_ID",
                    "SERIES_DIRECT"
                )

                putExtra(
                    "CATEGORY_NAME",
                    seriesName
                )

                putExtra(
                    "STREAM_NAME",
                    episodeName
                )
            }

        // ---------------------------------------------------------
        // OPEN PLAYER
        // ---------------------------------------------------------

        try {

            startActivity(
                playerIntent
            )

        } catch (
            e: Exception
        ) {

            Toast.makeText(
                this,
                "Unable to open episode: ${
                    e.localizedMessage
                        ?: "Unknown error"
                }",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // =============================================================
    // NO EPISODES
    // =============================================================

    private fun showNoEpisodes() {

        episodeNames.clear()
        episodeIds.clear()
        episodeExts.clear()

        episodeNames.add(
            "No episodes available"
        )

        recyclerViewEpisodes.adapter =
            NoEpisodesAdapter()

        recyclerViewEpisodes.requestFocus()
    }

    // =============================================================
    // NO EPISODES ADAPTER
    // =============================================================

    inner class NoEpisodesAdapter :
        RecyclerView.Adapter<NoEpisodesAdapter.ViewHolder>() {

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
        }

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

            return ViewHolder(
                view
            )
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {

            holder.titleView.text =
                "No episodes available"

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
    // FIRE TV / ANDROID TV BACK
    // =============================================================

    override fun dispatchKeyEvent(
        event: KeyEvent
    ): Boolean {

        if (
            event.action ==
            KeyEvent.ACTION_DOWN &&
            event.repeatCount == 0
        ) {

            if (
                event.keyCode ==
                KeyEvent.KEYCODE_BACK
            ) {

                finish()

                return true
            }
        }

        return super.dispatchKeyEvent(
            event
        )
    }

    // =============================================================
    // FIRST NON-EMPTY VALUE
    // =============================================================

    private fun firstNonEmpty(
        vararg values: String
    ): String {

        for (
            value in values
        ) {

            if (
                value.isNotBlank()
            ) {

                return value.trim()
            }
        }

        return ""
    }
}