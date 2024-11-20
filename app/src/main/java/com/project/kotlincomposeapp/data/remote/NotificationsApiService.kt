package com.project.kotlincomposeapp.data.remote

import com.project.kotlincomposeapp.data.local.entity.NotificationEntity
import retrofit2.http.GET

interface NotificationsApiService {
    @GET("notifications")
    suspend fun getNotifications(): List<NotificationEntity>
}