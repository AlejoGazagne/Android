package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.model.Notification
import com.project.kotlincomposeapp.data.repository.NotificationRepository

class NotificationViewModel : ViewModel() {

    private val notificationRepository = NotificationRepository()

    // LiveData para mantener las notificaciones
    private val _notifications: MutableLiveData<List<Notification>> = MutableLiveData()
    val notifications: LiveData<List<Notification>> get() = _notifications

    // LiveData para el conteo de notificaciones no leídas
    private val _unreadCount: MutableLiveData<Int> = MutableLiveData()
    val unreadCount: LiveData<Int> get() = _unreadCount

    init {
        // Cargar las notificaciones iniciales
        loadNotifications()
        updateUnreadCount()
    }

    // Cargar las notificaciones desde el repositorio
    private fun loadNotifications() {
        _notifications.value = notificationRepository.getAllNotifications()
    }

    // Marcar una notificación como leída
    fun markAsRead(notification: Notification) {
        notificationRepository.markAsRead(notification)
        updateUnreadCount()
        loadNotifications() // Recargar la lista de notificaciones para actualizar el estado
    }

    // Marcar todas las notificaciones como leídas
    fun markAllAsRead() {
        notificationRepository.markAllAsRead()
        updateUnreadCount()
        loadNotifications()
    }

    // Eliminar una notificación
    fun deleteNotification(notification: Notification) {
        notificationRepository.deleteNotification(notification)
        loadNotifications() // Recargar la lista después de eliminar
        updateUnreadCount()
    }

    // Agregar una nueva notificación
    fun addNotification(notification: Notification) {
        notificationRepository.addNotification(notification)
        loadNotifications() // Recargar para mostrar la nueva notificación
        updateUnreadCount()
    }

    // Actualizar el conteo de notificaciones no leídas
    private fun updateUnreadCount() {
        _unreadCount.value = if (notificationRepository.hasUnreadNotifications()) {
            notificationRepository.getAllNotifications().count { !it.isRead && !it.isDeleted }
        } else {
            0
        }
    }
}
