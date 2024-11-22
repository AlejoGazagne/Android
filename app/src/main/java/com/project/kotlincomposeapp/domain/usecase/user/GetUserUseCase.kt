package com.project.kotlincomposeapp.domain.usecase.user

import android.util.Log
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.model.UserModel
import com.project.kotlincomposeapp.domain.repository.UserLocalStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserLocalStorageRepository){
    operator fun invoke() : Flow<Resource<UserModel>> = flow {
        try {
            emit(Resource.Loading())
            Log.e("user case", "llego?")
            emit(
                Resource.Success(
                    data = repository.getUser()
                )
            )
        } catch (e: Exception) {
            emit(
                Resource.Error(e.message ?: "Unknown Error")
            )
        }
    }
}