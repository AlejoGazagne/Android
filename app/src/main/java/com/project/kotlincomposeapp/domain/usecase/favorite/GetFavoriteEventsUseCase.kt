package com.project.kotlincomposeapp.domain.usecase.favorite

import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.repository.FavoriteEventLocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteEventsUseCase @Inject constructor(private val repository: FavoriteEventLocalStorageRepository) {
    operator fun invoke() : Flow<Resource<MutableList<EventModel>>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    data = repository.getFavoriteEvents()
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(e.message ?: "Unknown Error")
            )
        }
    }
}