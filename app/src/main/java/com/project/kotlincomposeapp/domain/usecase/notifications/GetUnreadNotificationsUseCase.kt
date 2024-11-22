package com.project.kotlincomposeapp.domain.usecase.notifications

import com.project.kotlincomposeapp.domain.model.NotificationModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.repository.NotificationLocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUnreadNotificationsUseCase @Inject constructor(private val repository: NotificationLocalStorageRepository) {
    operator fun invoke() : Flow<Resource<MutableList<NotificationModel>>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    data = repository.getUnreadNotifications()
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(e.message ?: "Unknown Error")
            )
        }
    }
}