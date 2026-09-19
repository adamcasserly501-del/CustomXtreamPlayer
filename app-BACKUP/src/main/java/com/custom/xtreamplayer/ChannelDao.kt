package com.custom.xtreamplayer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChannelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannels(channels: List<ChannelEntity>)

    @Query("SELECT * FROM channels WHERE sectionType = :type AND categoryId = :catId ORDER BY name ASC")
    suspend fun getChannelsByCategory(type: String, catId: String): List<ChannelEntity>

    @Query("SELECT * FROM channels WHERE sectionType = :type AND isFavorite = 1 ORDER BY name ASC")
    suspend fun getFavorites(type: String): List<ChannelEntity>

    @Query("UPDATE channels SET isFavorite = :isFav WHERE streamId = :streamId")
    suspend fun setFavorite(streamId: String, isFav: Boolean)

    @Query("SELECT * FROM channels WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    suspend fun searchChannels(query: String): List<ChannelEntity>

    @Query("DELETE FROM channels WHERE sectionType = :type")
    suspend fun clearSection(type: String)

    @Query("DELETE FROM channels")
    suspend fun clearAll()
}