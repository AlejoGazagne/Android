package com.project.kotlincomposeapp.data.model

data class Notification(
    val id: Int,
    val title: String,
    val message: String,
    val date: String,
    var isRead: Boolean = false,
    var isDeleted: Boolean = false
)
