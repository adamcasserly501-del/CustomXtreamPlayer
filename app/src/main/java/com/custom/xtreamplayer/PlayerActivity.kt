package com.custom.xtreamplayer

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class PlayerActivity : AppCompatActivity() {

    // =============================================================
    // SERIES LIST
    // =============================================================

    private lateinit var recyclerView: RecyclerView
    private lateinit var titleView: TextView

    // =============================================================
    // VIDEO PLAYER
    // =============================================================

    private lateinit var playerView: PlayerView
    private lateinit var player: ExoPlayer

    // =============================================================
    // XTREAM LOGIN
    // =============================================================

    private var serverUrl = ""
    private var user = ""
    private var pass = ""

    // =============================================================
    // STREAM INFORMATION
    // =============================================================

    private var streamId = ""
    private var streamExt = ""
    private var sectionType = "VOD"
    private var categoryName = ""
    private var categoryId = ""

    // =============================================================
    // MODE
    // =============================================================

    private var isSeriesListMode = false
    private var isVideoMode = false

    // =============================================================
    // ON CREATE
    // =============================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ---------------------------------------------------------
        // LOAD LOGIN
        // ---------------------------------------------------------

        val prefs =
            getSharedPreferences(
                "XtreamPrefs",
                Context.MODE_PRIVATE
            )

        serverUrl =
            prefs.getString(
                "SERVER_URL",
                ""
            )
                ?.trimEnd('/')
                ?: ""

        user =
            prefs.getString(
                "USERNAME",
                ""
            )
                ?: ""

        pass =
            prefs.getString(
                "PASSWORD",
                ""
            )
                ?: ""

        // ---------------------------------------------------------
        // LOAD INTENT DATA
        // ---------------------------------------------------------

        streamId =
            intent.getStringExtra(
                "STREAM_ID"
            )
                ?.trim()
                ?: ""

        streamExt =
            intent.getStringExtra(
                "STREAM_EXT"
            )
                ?.trim()
                ?.trimStart('.')
                ?.lowercase()
                ?: ""

        sectionType =
            intent.getStringExtra(
                "SECTION_TYPE"
            )
                ?.uppercase()
                ?: "VOD"

        categoryId =
            intent.getStringExtra(
                "CATEGORY_ID"
            )
                ?.trim()
                ?: ""

        categoryName =
            intent.getStringExtra(
                "CATEGORY_NAME"
            )
                ?: ""

        // =========================================================
        // VALIDATE LOGIN
        // =========================================================

        if (
            serverUrl.isBlank() ||
            user.isBlank() ||
            pass.isBlank()
        ) {

            Toast.makeText(
                this,
                "Configuration error: Missing server details",
                Toast.LENGTH_LONG
            ).show()

            finish()
            return
        }

        // =========================================================
        // IMPORTANT:
        //
        // CHOOSE THE CORRECT LAYOUT
        // =========================================================

        /*
         * SERIES LIST
         *
         * A Series category opens PlayerActivity without
         * a STREAM_ID.
         */
        if (
            sectionType == "SERIES" &&
            streamId.isBlank()
        ) {

            isSeriesListMode = true

            setContentView(
                R.layout.activity_player
            )

            setupSeriesList()

            loadSeries()

            return
        }

        /*
         * ACTUAL VIDEO
         *
         * Movies and Series episodes arrive here with
         * a STREAM_ID.
         *
         * THIS MUST USE activity_video_player.xml.
         */
        if (streamId.isNotBlank()) {

            isVideoMode = true

            setContentView(
                R.layout.activity_video_player
            )

            setupVideoPlayer()

            playStream()

            return
        }

        // =========================================================
        // NOTHING TO DO
        // =========================================================

        Toast.makeText(
            this,
            "Missing stream information",
            Toast.LENGTH_LONG
        ).show()

        finish()
    }

    // =============================================================
    // SERIES LIST SETUP
    // =============================================================

    private fun setupSeriesList() {

        recyclerView =
            findViewById(
                R.id.recyclerViewStreams
            )

        titleView =
            findViewById(
                R.id.tvPlayerTitle
            )

        titleView.text =
            if (categoryName.isNotBlank()) {
                categoryName
            } else {
                "Series"
            }

        recyclerView.layoutManager =
            GridLayoutManager(
                this,
                6
            )

        recyclerView.itemAnimator = null

        recyclerView.isFocusable = true
        recyclerView.isFocusableInTouchMode = true
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

                val repository =
                    DataRepository(
                        this@PlayerActivity
                    )

                // -------------------------------------------------
                // FAVORITES
                // -------------------------------------------------

                if (
                    categoryId ==
                    "FAVORITES_ID"
                ) {

                    val favourites =
                        repository.getChannelsFromDb(
                            "SERIES",
                            "FAVORITES_ID"
                        )

                    recyclerView.adapter =
                        SeriesAdapter(
                            favourites
                        )

                    if (
                        favourites.isEmpty()
                    ) {

                        Toast.makeText(
                            this@PlayerActivity,
                            "No favourite series found",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    return@launch
                }

                // -------------------------------------------------
                // DOWNLOAD SERIES
                // -------------------------------------------------

                repository.syncCategoryStreams(
                    serverUrl = serverUrl,
                    user = user,
                    pass = pass,
                    sectionType = "SERIES",
                    categoryId = categoryId,
                    categoryName = categoryName
                )

                // -------------------------------------------------
                // READ FROM DATABASE
                // -------------------------------------------------

                val series =
                    repository.getChannelsFromDb(
                        "SERIES",
                        categoryId
                    )

                recyclerView.adapter =
                    SeriesAdapter(
                        series
                    )

                if (
                    series.isEmpty()
                ) {

                    Toast.makeText(
                        this@PlayerActivity,
                        "No series found in this category",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@PlayerActivity,
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
        private val series:
            List<ChannelEntity>
    ) :
        RecyclerView.Adapter<
            SeriesAdapter.ViewHolder
        >() {

        inner class ViewHolder(
            view: View
        ) :
            RecyclerView.ViewHolder(view) {

            val title: TextView =
                view.findViewById(
                    R.id.tvCardTitle
                )

            val poster: ImageView =
                view.findViewById(
                    R.id.ivCardPoster
                )

            init {

                view.isFocusable = true
                view.isFocusableInTouchMode = true
                view.isClickable = true

                view.setOnClickListener {

                    val position =
                        bindingAdapterPosition

                    if (
                        position ==
                        RecyclerView.NO_POSITION
                    ) {
                        return@setOnClickListener
                    }

                    val selectedSeries =
                        series[position]

                    val seriesId =
                        selectedSeries
                            .streamId
                            .trim()

                    if (
                        seriesId.isBlank()
                    ) {

                        Toast.makeText(
                            this@PlayerActivity,
                            "Missing series ID",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@setOnClickListener
                    }

                    // -------------------------------------------------
                    // OPEN EPISODES
                    // -------------------------------------------------

                    val intent =
                        Intent(
                            this@PlayerActivity,
                            SeriesActivity::class.java
                        )

                    intent.putExtra(
                        "SERIES_ID",
                        seriesId
                    )

                    intent.putExtra(
                        "SERIES_NAME",
                        selectedSeries.name
                    )

                    startActivity(intent)
                }
            }
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

            view.setBackgroundResource(
                R.drawable.dashboard_button_selector
            )

            return ViewHolder(
                view
            )
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {

            val item =
                series[position]

            holder.title.text =
                item.name

            holder.poster.setImageResource(
                android.R.drawable.ic_menu_gallery
            )

            if (
                item.iconUrl.isNotBlank()
            ) {

                holder.poster.load(
                    item.iconUrl
                ) {

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

        override fun getItemCount(): Int =
            series.size
    }

    // =============================================================
    // VIDEO PLAYER SETUP
    // =============================================================

    private fun setupVideoPlayer() {

        playerView =
            findViewById(
                R.id.videoPlayerView
            )

        val httpDataSourceFactory =
            DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(
                    true
                )
                .setConnectTimeoutMs(
                    15_000
                )
                .setReadTimeoutMs(
                    30_000
                )
                .setUserAgent(
                    "XtreamPlayer"
                )

        player =
            ExoPlayer
                .Builder(this)
                .setMediaSourceFactory(
                    androidx.media3.exoplayer.source
                        .DefaultMediaSourceFactory(
                            httpDataSourceFactory
                        )
                )
                .build()

        playerView.player =
            player

        playerView.isFocusable =
            true

        playerView.isFocusableInTouchMode =
            true

        playerView.keepScreenOn =
            true

        player.addListener(
            object :
                Player.Listener {

                override fun onPlaybackStateChanged(
                    playbackState: Int
                ) {

                    when (
                        playbackState
                    ) {

                        Player.STATE_IDLE -> {
                            playerView.keepScreenOn =
                                true
                        }

                        Player.STATE_BUFFERING -> {
                            playerView.keepScreenOn =
                                true
                        }

                        Player.STATE_READY -> {
                            playerView.keepScreenOn =
                                true
                        }

                        Player.STATE_ENDED -> {
                            playerView.keepScreenOn =
                                false
                        }
                    }
                }

                override fun onPlayerError(
                    error: PlaybackException
                ) {

                    val message =
                        when (
                            error.errorCode
                        ) {

                            PlaybackException
                                .ERROR_CODE_IO_NETWORK_CONNECTION_FAILED ->
                                "Network connection failed"

                            PlaybackException
                                .ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT ->
                                "Connection timed out"

                            PlaybackException
                                .ERROR_CODE_PARSING_CONTAINER_MALFORMED ->
                                "Video format is not supported"

                            PlaybackException
                                .ERROR_CODE_DECODER_INIT_FAILED ->
                                "Video decoder could not start"

                            PlaybackException
                                .ERROR_CODE_DECODING_FAILED ->
                                "Video decoding failed"

                            else ->
                                "Unable to play this video"
                        }

                    Toast.makeText(
                        this@PlayerActivity,
                        message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    // =============================================================
    // PLAY STREAM
    // =============================================================

    private fun playStream() {

        val streamUrl =
            buildStreamUrl()

        if (
            streamUrl.isBlank()
        ) {

            Toast.makeText(
                this,
                "Unable to create video URL",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        val mediaItemBuilder =
            MediaItem.Builder()
                .setUri(
                    Uri.parse(
                        streamUrl
                    )
                )

        when (
            streamExt.lowercase()
        ) {

            "m3u8" -> {

                mediaItemBuilder.setMimeType(
                    MimeTypes.APPLICATION_M3U8
                )
            }

            "mp4" -> {

                mediaItemBuilder.setMimeType(
                    MimeTypes.VIDEO_MP4
                )
            }

            "ts" -> {

                mediaItemBuilder.setMimeType(
                    MimeTypes.VIDEO_MP2T
                )
            }

            "mkv" -> {

                mediaItemBuilder.setMimeType(
                    MimeTypes.VIDEO_MATROSKA
                )
            }

            "webm" -> {

                mediaItemBuilder.setMimeType(
                    MimeTypes.VIDEO_WEBM
                )
            }
        }

        player.setMediaItem(
            mediaItemBuilder.build()
        )

        player.prepare()

        player.playWhenReady =
            true

        playerView.requestFocus()
    }

    // =============================================================
    // BUILD STREAM URL
    // =============================================================

    private fun buildStreamUrl(): String {

        val cleanServer =
            serverUrl.trimEnd('/')

        val encodedUser =
            Uri.encode(user)

        val encodedPass =
            Uri.encode(pass)

        val encodedStreamId =
            Uri.encode(streamId)

        val extension =
            streamExt
                .ifBlank {

                    when (
                        sectionType
                    ) {

                        "LIVE" ->
                            "ts"

                        else ->
                            "mp4"
                    }
                }
                .trimStart('.')

        return when (
            sectionType
        ) {

            "VOD" -> {

                "$cleanServer/movie/" +
                        "$encodedUser/" +
                        "$encodedPass/" +
                        "$encodedStreamId." +
                        extension
            }

            "SERIES" -> {

                "$cleanServer/series/" +
                        "$encodedUser/" +
                        "$encodedPass/" +
                        "$encodedStreamId." +
                        extension
            }

            "LIVE" -> {

                "$cleanServer/live/" +
                        "$encodedUser/" +
                        "$encodedPass/" +
                        "$encodedStreamId." +
                        extension
            }

            else -> {

                "$cleanServer/movie/" +
                        "$encodedUser/" +
                        "$encodedPass/" +
                        "$encodedStreamId." +
                        extension
            }
        }
    }

    // =============================================================
    // FIRESTICK BACK
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
    // STOP VIDEO
    // =============================================================

    override fun onStop() {

        super.onStop()

        if (
            isVideoMode &&
            ::player.isInitialized
        ) {

            player.pause()
        }
    }

    // =============================================================
    // DESTROY
    // =============================================================

    override fun onDestroy() {

        if (
            isVideoMode &&
            ::player.isInitialized
        ) {

            player.release()
        }

        super.onDestroy()
    }
}