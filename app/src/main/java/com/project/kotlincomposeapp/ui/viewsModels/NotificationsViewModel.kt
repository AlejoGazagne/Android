package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.model.Notification
import com.project.kotlincomposeapp.data.repository.NotificationRepository
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificationsViewModel : ViewModel() {

    private val repository = NotificationRepository

    /*// LiveData para mantener las notificaciones
    private val _notifications: MutableLiveData<List<Notification>> = MutableLiveData()
    val notifications: LiveData<List<Notification>> get() = _notifications*/

    // Cambiar MutableLiveData a MutableStateFlow
    private val _notifications: MutableStateFlow<List<Notification>> = MutableStateFlow(emptyList())
    val notifications: StateFlow<List<Notification>> get() = _notifications

    // LiveData para el conteo de notificaciones no leídas
    private val _unreadCount: MutableLiveData<Int> = MutableLiveData()
    val unreadCount: LiveData<Int> get() = _unreadCount

    init {
        loadNotifications()
        updateUnreadCount()
    }

    private fun loadNotifications() {
        _notifications.value = repository.getAllNotifications()
    }

    fun markAsRead(notification: Notification) {
        repository.markAsRead(notification)
        updateUnreadCount()
        val updateList = _notifications.value.map {
            if (it.id == notification.id) it.copy(isRead = true) else it
        }
        _notifications.value = updateList
        Log.d("NotificationViewModel", notifications.value.toString())
    }

    fun markAllAsRead() {
        repository.markAllAsRead()
        updateUnreadCount()
        _notifications.value = repository.getAllNotifications()
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

    fun reloadNotifications(){ _notifications.value = repository.getAllNotifications() }
}
