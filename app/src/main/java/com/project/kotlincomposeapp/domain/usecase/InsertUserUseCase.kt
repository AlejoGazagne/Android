package com.project.kotlincomposeapp.domain.usecase

import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.model.UserModel
import com.project.kotlincomposeapp.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(private val repository: LocalStorageRepository){
    operator fun invoke(user: UserModel) : Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            repository.insertUser(user)
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