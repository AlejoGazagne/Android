package com.project.kotlincomposeapp.domain.model

data class NotificationModel (
    val title: String,
    val message: String,
    val date: String,
    val isRead: Boolean,
    val isDeleted: Boolean
)