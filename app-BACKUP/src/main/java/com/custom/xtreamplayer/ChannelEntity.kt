package com.custom.xtreamplayer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey val streamId: String,
    val name: String,
    val sectionType: String, // e.g., "LIVE", "VOD", "SERIES"
    val categoryId: String,
    val iconUrl: String = "", // Added to fix the iconUrl unresolved reference
    val containerExtension: String = "mp4", // Added to fix the containerExtension unresolved reference
    val isFavorite: Boolean = false
)