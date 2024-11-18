package com.project.kotlincomposeapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "capacity") val capacity: Int,
    @ColumnInfo(name = "organizer") val organizer: String,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean
)



