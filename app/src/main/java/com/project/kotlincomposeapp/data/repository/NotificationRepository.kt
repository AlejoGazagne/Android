package com.project.kotlincomposeapp.data.repository

import com.project.kotlincomposeapp.data.model.Notification

object NotificationRepository {

    // Lista privada de notificaciones
    private val notifications = mutableListOf<Notification>(
        Notification(
            id = 1,
            title = "Bienvenido",
            message = "Gracias por unirte a nuestra aplicación.",
            date = "2024-10-04",
            isRead = false,
            isDeleted = false
        ),
        Notification(
            id = 2,
            title = "Nueva actualización",
            message = "Hay una nueva versión de la app disponible.",
            date = "2024-09-28",
            isRead = false,
            isDeleted = false
        ),
        Notification(
            id = 3,
            title = "Evento próximo",
            message = "No olvides tu evento de mañana a las 15:00",
            date = "2024-10-05",
            isRead = true,
            isDeleted = false
        )
    )


    // Obtener todas las notificaciones (que no están eliminadas)
    fun getAllNotifications(): List<Notification> {
        return notifications.filter { !it.isDeleted }
    }

    // Marcar notificación como leída
    fun markAsRead(notification: Notification) {
        val index = notifications.indexOf(notification)
        if (index != -1) {
            notifications[index].isRead = true
        }
    }

    // Marcar todas las notificaciones como leídas
    fun markAllAsRead() {
        notifications.forEach { it.isRead = true }
    }

    // Eliminar notificación
    fun deleteNotification(notification: Notification) {
        val index = notifications.indexOf(notification)
        if (index != -1) {
            notifications[index].isDeleted = true
        }
    }

    // Agregar una nueva notificación
    fun addNotification(notification: Notification) {
        notifications.add(notification)
    }

    // Verificar si hay notificaciones no leídas
    fun hasUnreadNotifications(): Boolean {
        return notifications.any { !it.isRead && !it.isDeleted }
    }
}
