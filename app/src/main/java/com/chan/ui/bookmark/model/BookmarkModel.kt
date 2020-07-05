package com.chan.ui.bookmark.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarkTable")
data class BookmarkModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val thumbnail: String,
    val imagePath: String,
    val subject: String,
    val price: Int,
    val rate: Float,
    val regTimeStamp: Long
)
