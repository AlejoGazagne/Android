package com.project.kotlincomposeapp.domain.usecase

import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.model.UserModel
import com.project.kotlincomposeapp.domain.repository.LocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveUserUseCase @Inject constructor (private val repository: LocalStorageRepository) {
    operator fun invoke(user: UserModel): Flow<Resource<UserModel>> = flow {
        try {
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    data = repository.saveUser(user)
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(e.message ?: "Unknown Error")
            )
        }
    }
}