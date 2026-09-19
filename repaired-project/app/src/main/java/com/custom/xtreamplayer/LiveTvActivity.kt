package com.custom.xtreamplayer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.coroutines.launch

class LiveTvActivity : AppCompatActivity() {

    private lateinit var channelList: RecyclerView
    private lateinit var playerView: PlayerView
    private lateinit var currentChannelText: TextView
    private lateinit var currentChannelLogo: ImageView
    private lateinit var playerStatus: TextView
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button

    private lateinit var repository: DataRepository
    private lateinit var player: ExoPlayer
    private lateinit var adapter: ChannelAdapter

    private var channels: List<ChannelEntity> = emptyList()
    private var selectedIndex = -1

    private var serverUrl = ""
    private var user = ""
    private var pass = ""
    private var categoryId = ""
    private var categoryName = "Live TV"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_tv)

        repository = DataRepository(this)

        channelList = findViewById(R.id.rvLiveChannels)
        playerView = findViewById(R.id.livePlayerView)
        currentChannelText = findViewById(R.id.tvCurrentChannel)
        currentChannelLogo = findViewById(R.id.ivCurrentChannelLogo)
        playerStatus = findViewById(R.id.tvPlayerStatus)
        previousButton = findViewById(R.id.btnPreviousChannel)
        nextButton = findViewById(R.id.btnNextChannel)

        channelList.layoutManager = LinearLayoutManager(this)
        channelList.itemAnimator = null

        val prefs = getSharedPreferences("XtreamPrefs", Context.MODE_PRIVATE)
        serverUrl = prefs.getString("SERVER_URL", "") ?: ""
        user = prefs.getString("USERNAME", "") ?: ""
        pass = prefs.getString("PASSWORD", "") ?: ""

        categoryId = intent.getStringExtra("CATEGORY_ID") ?: ""
        categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "Live TV"

        if (serverUrl.isBlank() || categoryId.isBlank()) {
            Toast.makeText(this, "Missing server or category information", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        player = ExoPlayer.Builder(this).build()
        playerView.player = player
        playerView.isFocusable = true
        playerView.isFocusableInTouchMode = true

        player.addListener(object : androidx.media3.common.Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                playerStatus.text = "Unable to play this channel"
            }
        })

        previousButton.setOnClickListener { selectChannel(selectedIndex - 1, true) }
        nextButton.setOnClickListener { selectChannel(selectedIndex + 1, true) }

        loadChannels()
    }

    private fun loadChannels() {
        lifecycleScope.launch {
            try {
                if (categoryId != "FAVORITES_ID") {
                    repository.syncCategoryStreams(
                        serverUrl,
                        user,
                        pass,
                        "LIVE",
                        categoryId,
                        categoryName
                    )
                }

                channels = repository.getChannelsFromDb("LIVE", categoryId)

                if (channels.isEmpty()) {
                    playerStatus.text = "No channels found in this category"
                    return@launch
                }

                adapter = ChannelAdapter(
                    channels = channels,
                    onClick = { channel ->
                        val index = channels.indexOfFirst { it.streamId == channel.streamId }
                        selectChannel(index, true)
                    },
                    onLongClick = { channel ->
                        toggleFavourite(channel)
                    }
                )

                channelList.adapter = adapter

                // Always start on the first real channel.
                selectChannel(0, false)
            } catch (e: Exception) {
                playerStatus.text = "Error loading channels"
                Toast.makeText(
                    this@LiveTvActivity,
                    "Error loading channels: ${e.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun toggleFavourite(channel: ChannelEntity) {
        lifecycleScope.launch {
            repository.setFavorite(channel.streamId, !channel.isFavorite)
            // Reload this category so the adapter gets the new favourite state.
            val updated = repository.getChannelsFromDb("LIVE", categoryId)
            val currentId = channels.getOrNull(selectedIndex)?.streamId
            channels = updated
            adapter = ChannelAdapter(
                channels,
                onClick = { c ->
                    selectChannel(channels.indexOfFirst { it.streamId == c.streamId }, true)
                },
                onLongClick = { c -> toggleFavourite(c) }
            )
            channelList.adapter = adapter
            val newIndex = channels.indexOfFirst { it.streamId == currentId }
            if (newIndex >= 0) selectChannel(newIndex, false)
        }
    }

    private fun selectChannel(index: Int, moveFocusToPlayer: Boolean) {
        if (index !in channels.indices) return

        selectedIndex = index
        val channel = channels[index]

        // The same channel ID drives the list highlight, player URL and artwork.
        adapter.setSelectedChannel(channel.streamId)
        channelList.scrollToPosition(index)

        currentChannelText.text = channel.name
        playerStatus.text = "Loading…"

        currentChannelLogo.setImageResource(android.R.drawable.ic_menu_gallery)
        if (channel.iconUrl.isNotBlank()) {
            currentChannelLogo.load(channel.iconUrl) {
                crossfade(true)
                placeholder(android.R.drawable.ic_menu_gallery)
                error(android.R.drawable.ic_menu_gallery)
            }
        }

        val streamUrl = buildLiveStreamUrl(channel)
        val mediaItemBuilder = MediaItem.Builder().setUri(streamUrl)

        if (streamUrl.contains(".m3u8", ignoreCase = true)) {
            mediaItemBuilder.setMimeType(MimeTypes.APPLICATION_M3U8)
        }

        player.setMediaItem(mediaItemBuilder.build())
        player.prepare()
        player.playWhenReady = true
        playerStatus.text = ""

        if (moveFocusToPlayer) {
            playerView.requestFocus()
        }
    }

    private fun buildLiveStreamUrl(channel: ChannelEntity): String {
        val cleanServer = serverUrl.trimEnd('/')
        val encodedUser = Uri.encode(user)
        val encodedPass = Uri.encode(pass)
        val extension = channel.containerExtension.ifBlank { "ts" }.trimStart('.')
        return "$cleanServer/live/$encodedUser/$encodedPass/${channel.streamId}.$extension"
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
            if (playerView.hasFocus()) {
                when (event.keyCode) {
                    KeyEvent.KEYCODE_DPAD_UP -> {
                        if (selectedIndex > 0) {
                            selectChannel(selectedIndex - 1, true)
                            return true
                        }
                    }
                    KeyEvent.KEYCODE_DPAD_DOWN -> {
                        if (selectedIndex < channels.lastIndex) {
                            selectChannel(selectedIndex + 1, true)
                            return true
                        }
                    }
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }
}
