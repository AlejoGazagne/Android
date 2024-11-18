package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.local.entity.NotificationEntity
import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor() : ViewModel() {
//
//    private val repository = NotificationRepository
//
//    // LiveData para mantener las notificaciones
//    private val _notifications: MutableLiveData<List<NotificationEntity>> = MutableLiveData()
//    val notifications: LiveData<List<NotificationEntity>> get() = _notifications
//
//    // LiveData para el conteo de notificaciones no leídas
//    private val _unreadCount: MutableLiveData<Int> = MutableLiveData()
//    val unreadCount: LiveData<Int> get() = _unreadCount
//
//    init {
//        loadNotifications()
//        updateUnreadCount()
//    }
//
//    fun loadNotifications() {
//        _notifications.value = repository.getAllNotifications()
//    }
//
//    fun markAsRead(notification: NotificationEntity) {
//        repository.markAsRead(notification)
//        updateUnreadCount()
//        _notifications.value = _notifications.value?.map {
//            if (it.id == notification.id) it.copy(isRead = true) else it
//        }
//        Log.d("NotificationViewModel", notifications.value.toString())
//    }
//
//    fun markAllAsRead() {
//        repository.markAllAsRead()
//        updateUnreadCount()
//        _notifications.value = repository.getAllNotifications()
//    }
//
//    // Eliminar una notificación
//    fun deleteNotification(notification: NotificationEntity) {
//        repository.deleteNotification(notification)
//        _notifications.value = repository.getAllNotifications()
//        updateUnreadCount()
//    }
//
//    // Agregar una nueva notificación
//    fun addNotification(notification: NotificationEntity) {
//        repository.addNotification(notification)
//        _notifications.value = repository.getAllNotifications()
//        updateUnreadCount()
//    }
//
//    // Actualizar el conteo de notificaciones no leídas
//    private fun updateUnreadCount() {
//        _unreadCount.value = if (repository.hasUnreadNotifications()) {
//            repository.getAllNotifications().count { !it.isRead && !it.isDeleted }
//        } else {
//            0
//        }
//    }
}
