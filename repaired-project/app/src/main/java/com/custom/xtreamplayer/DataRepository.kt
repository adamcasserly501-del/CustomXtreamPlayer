package com.custom.xtreamplayer

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class DataRepository(context: Context) {

    private val channelDao = AppDatabase.getDatabase(context).channelDao()

    suspend fun syncCategoryStreams(
        serverUrl: String,
        user: String,
        pass: String,
        sectionType: String,
        categoryId: String,
        categoryName: String
    ) = withContext(Dispatchers.IO) {

        val type = sectionType.uppercase()
        val cleanServer = serverUrl.trimEnd('/')

        val action = when (type) {
            "VOD" -> "get_vod_streams"
            "SERIES" -> "get_series"
            else -> "get_live_streams"
        }

        val url = Uri.parse("$cleanServer/player_api.php").buildUpon()
            .appendQueryParameter("username", user)
            .appendQueryParameter("password", pass)
            .appendQueryParameter("action", action)
            .appendQueryParameter("category_id", categoryId)
            .build()
            .toString()

        val connection = (URL(url).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 15_000
            readTimeout = 30_000
            useCaches = false
        }

        try {
            if (connection.responseCode !in 200..299) {
                throw IllegalStateException("Server returned HTTP ${connection.responseCode}")
            }

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(response)

            // Keep favourite state for channels already in this category.
            val previousFavouriteIds = channelDao.getChannelsByCategory(type, categoryId)
                .filter { it.isFavorite }
                .map { it.streamId }
                .toSet()

            val channels = ArrayList<ChannelEntity>(jsonArray.length())

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.optJSONObject(i) ?: continue

                val streamId = when (type) {
                    "SERIES" -> obj.optString("series_id")
                    else -> obj.optString("stream_id")
                }.trim()

                if (streamId.isEmpty()) continue

                channels += ChannelEntity(
                    streamId = streamId,
                    name = obj.optString("name", "Unknown").trim().ifEmpty { "Unknown" },
                    sectionType = type,
                    categoryId = categoryId,
                    iconUrl = obj.optString("stream_icon", "").trim(),
                    containerExtension = obj.optString(
                        "container_extension",
                        if (type == "LIVE") "ts" else "mp4"
                    ).trim().ifEmpty { if (type == "LIVE") "ts" else "mp4" },
                    isFavorite = streamId in previousFavouriteIds
                )
            }

            // Replace only this category. Other categories/favourites remain cached.
            channelDao.clearCategory(type, categoryId)
            if (channels.isNotEmpty()) {
                channelDao.insertChannels(channels)
            }
        } finally {
            connection.disconnect()
        }
    }

    suspend fun getChannelsFromDb(type: String, categoryId: String): List<ChannelEntity> =
        withContext(Dispatchers.IO) {
            if (categoryId == "FAVORITES_ID") {
                channelDao.getFavorites(type.uppercase())
            } else {
                channelDao.getChannelsByCategory(type.uppercase(), categoryId)
            }
        }

    suspend fun setFavorite(streamId: String, favorite: Boolean) {
        withContext(Dispatchers.IO) {
            channelDao.setFavorite(streamId, favorite)
        }
    }
}
