package com.project.kotlincomposeapp.domain.repository

import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.NotificationModel

interface LocalStorageRepository {

        suspend fun getEvents(): MutableList<EventModel>

        suspend fun getEventByTitle(title: String): MutableList<EventModel>

        suspend fun getEventsByLocation(location: String): MutableList<EventModel>

        suspend fun getUnreadNotifications(): MutableList<NotificationModel>

        suspend fun getNotifications(): MutableList<NotificationModel>

        suspend fun getFavoriteEvents(): MutableList<EventModel>

        suspend fun toggleFavoriteEvent(event: EventModel)

        suspend fun markNotificationAsRead(notification: NotificationModel)

        suspend fun saveNotification(notification: NotificationModel)

        suspend fun deleteNotification(notification: NotificationModel)
}