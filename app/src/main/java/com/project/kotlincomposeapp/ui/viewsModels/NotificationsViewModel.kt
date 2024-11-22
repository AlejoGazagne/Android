package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.project.kotlincomposeapp.domain.model.NotificationModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.usecase.notifications.GetNotificationsUseCase
import com.project.kotlincomposeapp.domain.usecase.notifications.GetUnreadNotificationsUseCase
import com.project.kotlincomposeapp.domain.usecase.notifications.MarkAllNotificationsAsReadUseCase
import com.project.kotlincomposeapp.domain.usecase.notifications.MarkNotificationAsReadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markNotificationAsReadUseCase: MarkNotificationAsReadUseCase,
    private val unreadNotificationsUseCase: GetUnreadNotificationsUseCase,
    private val markAllNotificationsAsReadUseCase: MarkAllNotificationsAsReadUseCase
) : ViewModel() {

    private val _notifications = MutableStateFlow<Resource<MutableList<NotificationModel>>>(Resource.Loading())
    val stateNotifications: StateFlow<Resource<MutableList<NotificationModel>>> = _notifications

    private val _unreadNotifications = MutableLiveData<Int>()
    val unreadNotifications: LiveData<Int> = _unreadNotifications

    init {
        fetchNotifications()
    }

    private fun fetchNotifications(){
        viewModelScope.launch {
            getNotificationsUseCase().collect { resource ->
                Log.d("e", "Resource emitted: $resource")
                _notifications.value = resource
            }
        }
    }

    fun fetchUnreadNotifications(){
        viewModelScope.launch {
            unreadNotificationsUseCase().collect { unreadNotifications ->
                _unreadNotifications.value = unreadNotifications.data?.size ?: 0
            }
        }
    }

    fun markNotificationAsRead(notification: NotificationModel){
        viewModelScope.launch {
            markNotificationAsReadUseCase(notification).collect { result ->
                if(result is Resource.Success){
                    val updatedList = (_notifications.value as? Resource.Success<MutableList<NotificationModel>>)?.data?.map {
                        if(it.title == notification.title){
                            it.copy(isRead = true)
                        } else it
                    }?.toMutableList()
                    _notifications.value = Resource.Success(updatedList ?: mutableListOf())
                    fetchUnreadNotifications()
                }
            }
        }
    }

    fun markAllNotificationsAsRead(){
        viewModelScope.launch {
            markAllNotificationsAsReadUseCase().collect { result ->
                if(result is Resource.Success){
                    val updatedList = (_notifications.value as? Resource.Success<MutableList<NotificationModel>>)?.data?.map {
                        it.copy(isRead = true)
                    }?.toMutableList()
                    _notifications.value = Resource.Success(updatedList ?: mutableListOf())
                    fetchUnreadNotifications()
                }
            }
        }
    }
}
