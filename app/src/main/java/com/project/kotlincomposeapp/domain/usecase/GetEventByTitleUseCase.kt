package com.project.kotlincomposeapp.domain.usecase

import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEventByTitleUseCase @Inject constructor(private val repository: LocalStorageRepository) {
    suspend operator fun invoke(title: String) : Flow<Resource<MutableList<EventModel>>> = flow{
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    data = repository.getEventByTitle(title)
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(e.message ?: "Unknown Error")
            )
        }
    }
}