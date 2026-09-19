package com.custom.xtreamplayer

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "channels",
    indices = [
        Index(value = ["sectionType", "categoryId"]),
        Index(value = ["sectionType", "isFavorite"])
    ]
)
data class ChannelEntity(
    @PrimaryKey
    val streamId: String,
    val name: String,
    val sectionType: String,
    val categoryId: String,
    val iconUrl: String = "",
    val containerExtension: String = "ts",
    val isFavorite: Boolean = false
)
