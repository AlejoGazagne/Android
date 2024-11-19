package com.project.kotlincomposeapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "message") var message: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "isRead") var isRead: Boolean = false,
    @ColumnInfo(name = "isDeleted") var isDeleted: Boolean = false
)
