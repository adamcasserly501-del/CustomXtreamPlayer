package com.custom.xtreamplayer

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class DataRepository(context: Context) {
    
    // Connects to your Room Database and ChannelDao
    private val channelDao = AppDatabase.getDatabase(context).channelDao()

    suspend fun syncCategoryStreams(serverUrl: String, user: String, pass: String, sectionType: String, categoryId: String, categoryName: String) {
        withContext(Dispatchers.IO) {
            try {
                val cleanServer = serverUrl.trimEnd('/')
                val action = when (sectionType.uppercase()) {
                    "VOD" -> "get_vod_streams"
                    "SERIES" -> "get_series"
                    else -> "get_live_streams"
                }

                val urlString = "$cleanServer/player_api.php?username=$user&password=$pass&action=$action&category_id=$categoryId"
                val connection = URL(urlString).openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.connect()

                if (connection.responseCode == 200) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val jsonArray = JSONArray(response)
                    val channelsToInsert = mutableListOf<ChannelEntity>()

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        
                        val streamId = if (sectionType == "SERIES") obj.optString("series_id") else obj.optString("stream_id")
                        val name = obj.optString("name", "Unknown")
                        val iconUrl = obj.optString("stream_icon", "")
                        val ext = obj.optString("container_extension", "mp4")

                        // Explicit named arguments prevent all Type Mismatch errors
                        channelsToInsert.add(
                            ChannelEntity(
                                streamId = streamId,
                                name = name,
                                sectionType = sectionType,
                                categoryId = categoryId,
                                iconUrl = iconUrl,
                                containerExtension = ext,
                                isFavorite = false
                            )
                        )
                    }
                    
                    channelDao.clearSection(sectionType)
                    channelDao.insertChannels(channelsToInsert)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getChannelsFromDb(type: String, categoryId: String): List<ChannelEntity> {
        return withContext(Dispatchers.IO) {
            if (categoryId == "FAVORITES_ID") {
                channelDao.getFavorites(type)
            } else {
                channelDao.getChannelsByCategory(type, categoryId)
            }
        }
    }
}