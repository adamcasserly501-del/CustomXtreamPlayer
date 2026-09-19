package com.custom.xtreamplayer

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class DataRepository(context: Context) {

    private val channelDao =
        AppDatabase.getDatabase(context).channelDao()

    /**
     * Download streams/items for one category and save them
     * into the local Room database.
     *
     * LIVE:
     *     get_live_streams
     *
     * VOD:
     *     get_vod_streams
     *
     * SERIES:
     *     get_series
     */
    suspend fun syncCategoryStreams(
        serverUrl: String,
        user: String,
        pass: String,
        sectionType: String,
        categoryId: String,
        categoryName: String
    ) = withContext(Dispatchers.IO) {

        val type =
            sectionType
                .trim()
                .uppercase()

        val cleanServer =
            serverUrl.trimEnd('/')

        /*
         * Select the correct Xtream API action.
         */
        val action =
            when (type) {

                "VOD" ->
                    "get_vod_streams"

                "SERIES" ->
                    "get_series"

                else ->
                    "get_live_streams"
            }

        /*
         * Build API URL using Uri.Builder.
         *
         * This safely encodes usernames, passwords and
         * category IDs.
         */
        val url =
            Uri.parse(
                "$cleanServer/player_api.php"
            )
                .buildUpon()
                .appendQueryParameter(
                    "username",
                    user
                )
                .appendQueryParameter(
                    "password",
                    pass
                )
                .appendQueryParameter(
                    "action",
                    action
                )
                .appendQueryParameter(
                    "category_id",
                    categoryId
                )
                .build()
                .toString()

        val connection =
            (URL(url).openConnection()
                    as HttpURLConnection).apply {

                requestMethod = "GET"

                connectTimeout = 15_000

                readTimeout = 30_000

                useCaches = false

                doInput = true

                instanceFollowRedirects = true
            }

        try {

            /*
             * Check HTTP response.
             */
            if (connection.responseCode !in 200..299) {

                throw IllegalStateException(
                    "Server returned HTTP ${connection.responseCode}"
                )
            }

            /*
             * Read server response.
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
             * Xtream should return a JSON array.
             */
            val jsonArray =
                try {

                    JSONArray(response)

                } catch (e: Exception) {

                    throw IllegalStateException(
                        "Invalid server response",
                        e
                    )
                }

            /*
             * =====================================================
             * KEEP EXISTING FAVOURITE STATE
             * =====================================================
             *
             * Before replacing the category, remember which
             * items were favourites.
             */
            val previousFavouriteIds =
                channelDao
                    .getChannelsByCategory(
                        type,
                        categoryId
                    )
                    .filter {
                        it.isFavorite
                    }
                    .map {
                        it.streamId
                    }
                    .toSet()

            val channels =
                ArrayList<ChannelEntity>(
                    jsonArray.length()
                )

            /*
             * =====================================================
             * CONVERT API ITEMS
             * =====================================================
             */
            for (i in 0 until jsonArray.length()) {

                val obj =
                    jsonArray.optJSONObject(i)
                        ?: continue

                // =================================================
                // STREAM / SERIES ID
                // =================================================

                val streamId =
                    when (type) {

                        /*
                         * Series use series_id.
                         */
                        "SERIES" -> {

                            firstNonEmpty(
                                obj.optString(
                                    "series_id",
                                    ""
                                ),

                                /*
                                 * Some providers can use
                                 * stream_id even for series.
                                 */
                                obj.optString(
                                    "stream_id",
                                    ""
                                )
                            )
                        }

                        /*
                         * Movies and Live TV use stream_id.
                         */
                        else -> {

                            obj.optString(
                                "stream_id",
                                ""
                            )
                        }
                    }
                        .trim()

                /*
                 * Never save an item without an ID.
                 */
                if (streamId.isBlank()) {
                    continue
                }

                // =================================================
                // NAME
                // =================================================

                val name =
                    obj.optString(
                        "name",
                        ""
                    )
                        .trim()
                        .ifEmpty {

                            when (type) {

                                "SERIES" ->
                                    "Unknown Series"

                                "VOD" ->
                                    "Unknown Movie"

                                else ->
                                    "Unknown Channel"
                            }
                        }

                // =================================================
                // ARTWORK
                // =================================================

                /*
                 * Different Xtream providers use different
                 * artwork fields.
                 *
                 * SERIES commonly:
                 *
                 *     cover
                 *     cover_big
                 *
                 * MOVIES commonly:
                 *
                 *     stream_icon
                 *     cover
                 *     cover_big
                 *
                 * LIVE commonly:
                 *
                 *     stream_icon
                 */
                val iconUrl =
                    getArtworkUrl(
                        obj,
                        type
                    )

                // =================================================
                // CONTAINER EXTENSION
                // =================================================

                val defaultExtension =
                    when (type) {

                        "LIVE" ->
                            "ts"

                        else ->
                            "mp4"
                    }

                val containerExtension =
                    firstNonEmpty(
                        obj.optString(
                            "container_extension",
                            ""
                        ),
                        defaultExtension
                    )
                        .trim()
                        .trimStart('.')
                        .lowercase()

                // =================================================
                // CREATE DATABASE ENTITY
                // =================================================

                channels += ChannelEntity(

                    streamId =
                        streamId,

                    name =
                        name,

                    sectionType =
                        type,

                    categoryId =
                        categoryId,

                    iconUrl =
                        iconUrl,

                    containerExtension =
                        containerExtension,

                    isFavorite =
                        streamId in previousFavouriteIds
                )
            }

            // =====================================================
            // REPLACE ONLY THIS CATEGORY
            // =====================================================

            /*
             * Do NOT clear the entire database.
             *
             * Only the category currently being refreshed
             * is replaced.
             */
            channelDao.clearCategory(
                type,
                categoryId
            )

            if (channels.isNotEmpty()) {

                channelDao.insertChannels(
                    channels
                )
            }

        } finally {

            connection.disconnect()
        }
    }

    // =============================================================
    // GET ARTWORK URL
    // =============================================================

    /**
     * Extract artwork from an Xtream API object.
     *
     * Different IPTV providers return artwork using
     * different JSON field names.
     */
    private fun getArtworkUrl(
        obj: org.json.JSONObject,
        type: String
    ): String {

        /*
         * =========================================================
         * SERIES
         * =========================================================
         */
        if (type == "SERIES") {

            return firstNonEmpty(

                obj.optString(
                    "cover",
                    ""
                ),

                obj.optString(
                    "cover_big",
                    ""
                ),

                obj.optString(
                    "stream_icon",
                    ""
                ),

                obj.optString(
                    "movie_image",
                    ""
                ),

                obj.optString(
                    "poster",
                    ""
                ),

                obj.optString(
                    "poster_path",
                    ""
                ),

                obj.optString(
                    "backdrop_path",
                    ""
                )
            ).trim()
        }

        /*
         * =========================================================
         * MOVIES
         * =========================================================
         */
        if (type == "VOD") {

            return firstNonEmpty(

                obj.optString(
                    "stream_icon",
                    ""
                ),

                obj.optString(
                    "cover",
                    ""
                ),

                obj.optString(
                    "cover_big",
                    ""
                ),

                obj.optString(
                    "movie_image",
                    ""
                ),

                obj.optString(
                    "poster",
                    ""
                ),

                obj.optString(
                    "poster_path",
                    ""
                ),

                obj.optString(
                    "backdrop_path",
                    ""
                )
            ).trim()
        }

        /*
         * =========================================================
         * LIVE TV
         * =========================================================
         */
        return firstNonEmpty(

            obj.optString(
                "stream_icon",
                ""
            ),

            obj.optString(
                "icon",
                ""
            ),

            obj.optString(
                "logo",
                ""
            )
        ).trim()
    }

    // =============================================================
    // GET ITEMS FROM DATABASE
    // =============================================================

    /**
     * Return cached items from Room.
     *
     * FAVORITES_ID:
     *     returns only favourites.
     *
     * Normal category:
     *     returns items belonging to that category.
     */
    suspend fun getChannelsFromDb(
        type: String,
        categoryId: String
    ): List<ChannelEntity> =
        withContext(Dispatchers.IO) {

            val cleanType =
                type
                    .trim()
                    .uppercase()

            if (
                categoryId
                    .trim()
                    .uppercase() ==
                "FAVORITES_ID"
            ) {

                channelDao.getFavorites(
                    cleanType
                )

            } else {

                channelDao.getChannelsByCategory(
                    cleanType,
                    categoryId
                )
            }
        }

    // =============================================================
    // SET FAVOURITE
    // =============================================================

    /**
     * Change favourite state for an item.
     */
    suspend fun setFavorite(
        streamId: String,
        favorite: Boolean
    ) = withContext(Dispatchers.IO) {

        channelDao.setFavorite(
            streamId,
            favorite
        )
    }

    // =============================================================
    // SEARCH
    // =============================================================

    /**
     * Search cached items by name.
     *
     * SearchActivity uses this method.
     */
    suspend fun searchChannels(
        query: String
    ): List<ChannelEntity> =
        withContext(Dispatchers.IO) {

            channelDao.searchChannels(
                query.trim()
            )
        }

    // =============================================================
    // FIRST NON-EMPTY VALUE
    // =============================================================

    /**
     * Return the first non-empty string.
     */
    private fun firstNonEmpty(
        vararg values: String
    ): String {

        for (value in values) {

            if (value.isNotBlank()) {

                return value.trim()
            }
        }

        return ""
    }
}