package com.project.kotlincomposeapp.domain.usecase.notifications

import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.repository.NotificationLocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MarkAllNotificationsAsReadUseCase @Inject constructor(private val repository: NotificationLocalStorageRepository) {
    operator fun invoke() : Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            repository.getUnreadNotifications().forEach {
                repository.markNotificationAsRead(it)
            }
            emit(
                Resource.Success(
                    data = Unit
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(e.message ?: "Unknown Error")
            )
        }

    }
}