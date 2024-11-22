package com.project.kotlincomposeapp.domain.repository

import com.project.kotlincomposeapp.domain.model.UserModel

interface UserLocalStorageRepository {

    suspend fun login(email: String, password: String): UserModel

    suspend fun getUser(): UserModel

    suspend fun updateUser(user: UserModel)

    suspend fun deleteAllUsers()

    suspend fun registerUser(user: UserModel): UserModel

    suspend fun saveUser(user: UserModel): UserModel

}