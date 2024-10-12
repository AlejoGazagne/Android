package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.model.Notification
import com.project.kotlincomposeapp.data.repository.NotificationRepository

class NotificationViewModel : ViewModel() {

    private val repository = NotificationRepository

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
    fun loadNotifications() {
        _notifications.value = repository.getAllNotifications()
    }

    fun markAsRead(notification: Notification) {
        repository.markAsRead(notification)
        updateUnreadCount()
        _notifications.value = _notifications.value?.map {
            if (it.id == notification.id) it.copy(isRead = true) else it
        }
    }

    fun markAllAsRead() {
        repository.markAllAsRead()
        updateUnreadCount()
        _notifications.value = repository.getAllNotifications() // Asegúrate de que esto retorna una nueva lista
    }

    // Eliminar una notificación
    fun deleteNotification(notification: Notification) {
        repository.deleteNotification(notification)
        _notifications.value = repository.getAllNotifications()
        updateUnreadCount()
    }

    // Agregar una nueva notificación
    fun addNotification(notification: Notification) {
        repository.addNotification(notification)
        _notifications.value = repository.getAllNotifications()
        updateUnreadCount()
    }

    // Actualizar el conteo de notificaciones no leídas
    private fun updateUnreadCount() {
        _unreadCount.value = if (repository.hasUnreadNotifications()) {
            repository.getAllNotifications().count { !it.isRead && !it.isDeleted }
        } else {
            0
        }
    }
}
