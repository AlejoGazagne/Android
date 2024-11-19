package com.project.kotlincomposeapp.domain.usecase

import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ToggleFavoriteEventUseCase @Inject constructor(private val repository: LocalStorageRepository){
    operator fun invoke(event: EventModel) : Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            repository.toggleFavoriteEvent(event)
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