package com.project.kotlincomposeapp.ui.viewsModels

import android.app.Notification
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.local.entity.NotificationEntity
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.project.kotlincomposeapp.domain.model.NotificationModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.usecase.GetNotificationsUseCase
import com.project.kotlincomposeapp.domain.usecase.GetUnreadNotificationsUseCase
import com.project.kotlincomposeapp.domain.usecase.MarkAllNotificationsAsReadUseCase
import com.project.kotlincomposeapp.domain.usecase.MarkNotificationAsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    private val unreadNotificationsUseCase: GetUnreadNotificationsUseCase,
    private val markAllNotificationsAsReadUseCase: MarkAllNotificationsAsReadUseCase
) : ViewModel() {

    // Cambiar MutableLiveData a MutableStateFlow
    private val _notifications: MutableStateFlow<List<NotificationModel>> = MutableStateFlow(emptyList())
    val notifications: StateFlow<List<NotificationModel>> get() = _notifications

    // LiveData para el conteo de notificaciones no leídas
    private val _unreadCount: MutableLiveData<Int> = MutableLiveData()
    val unreadCount: LiveData<Int> get() = _unreadCount

    // Estado de consulta
    private val _notificationState = MutableStateFlow<Resource<MutableList<NotificationModel>>>(Resource.Loading())
    val notificationState: StateFlow<Resource<MutableList<NotificationModel>>> get() = _notificationState

    init {
        loadNotifications()
        updateUnreadCount()
    }

    private fun loadNotifications() {
        viewModelScope.launch {
            getNotificationsUseCase().onEach { result ->
                _notificationState.value = result
                when (result) {
                    is Resource.Success -> {
                        // Actualiza el MutableStateFlow con las notificaciones recibidas
                        _notifications.value = result.data ?: emptyList()
                    }
                    is Resource.Error -> {
                    // Maneja el error (por ejemplo, mostrando un mensaje)
                    // Aquí podrías agregar un MutableStateFlow o LiveData para el manejo de errores
                    }
                    is Resource.Loading -> {
                    // Maneja el estado de carga si es necesario
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun markAsRead(notification: NotificationModel) {
        markNotificationAsReadUseCase(notification)
        //updateUnreadCount()
        val updateList = _notifications.value.map {
            if (it == notification) it.copy(isRead = true) else it
        }
        _notifications.value = updateList
    }

    fun markAllAsRead() {
        markAllNotificationsAsReadUseCase()
        updateUnreadCount()
        loadNotifications()
    }

    // Eliminar una notificación
    /*fun deleteNotification(notification: Notification) {
        repository.deleteNotification(notification)
        _notifications.value = repository.getAllNotifications()
        updateUnreadCount()
    }*/

    // Agregar una nueva notificación
    /*fun addNotification(notification: Notification) {
        repository.addNotification(notification)
        _notifications.value = repository.getAllNotifications()
        updateUnreadCount()
    }*/

    // Actualizar el conteo de notificaciones no leídas
    private fun updateUnreadCount() {
        unreadNotificationsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _unreadCount.value = result.data?.size ?: 0
                }
                is Resource.Error -> {
                    // Maneja el error (por ejemplo, mostrando un mensaje)
                    // Aquí podrías agregar un MutableStateFlow o LiveData para el manejo de errores
                }
                is Resource.Loading -> {
                    // Maneja el estado de carga si es necesario
                }
            }
        }.launchIn(viewModelScope)
    }

    fun reloadNotifications(){ loadNotifications() }
}
