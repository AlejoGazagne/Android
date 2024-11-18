package com.project.kotlincomposeapp.domain.usecase

import com.project.kotlincomposeapp.domain.model.NotificationModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveNotificationUseCase @Inject constructor(private val repository: LocalStorageRepository) {
    operator fun invoke(notification: NotificationModel) : Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            repository.saveNotification(notification)
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