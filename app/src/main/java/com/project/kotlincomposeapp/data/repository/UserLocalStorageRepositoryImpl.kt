package com.project.kotlincomposeapp.data.repository

import com.project.kotlincomposeapp.data.local.dao.UserDao
import com.project.kotlincomposeapp.data.local.entity.UserEntity
import com.project.kotlincomposeapp.data.remote.UserApiService
import com.project.kotlincomposeapp.domain.model.UserModel
import com.project.kotlincomposeapp.domain.repository.UserLocalStorageRepository
import javax.inject.Inject

class UserLocalStorageRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userApi: UserApiService
): UserLocalStorageRepository {
    override suspend fun getUser(): UserModel {
        try {
            val user = userDao.getUser();
            val userModel = UserModel(user.name, user.email, user.password)
            return userModel
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun login(email: String, password: String): UserModel {
        try {
            val userApi = userApi.getUserByEmailAndPassword(email, password)
            val user = userApi.first()

            userDao.deleteAllUsers()
            userDao.insertUser(UserEntity(0, user.name, user.email, user.password))
            val userModel = UserModel(user.name, user.email, user.password)

            return userModel
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateUser(user: UserModel) {
        try {
            userDao.updateUser(user.name, user.email, user.password)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteAllUsers() {
        try {
            userDao.deleteAllUsers()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun registerUser(user: UserModel): UserModel {
        try {
            val userList: List<UserEntity> = userApi.getUsers()
            userList.forEach{
                if(it.email == user.email) {
                    throw Exception("User already exists")
                }
            }

            val userEntity = userApi.createUser(mapOf("id" to user.email.hashCode().toString(),"name" to user.name, "email" to user.email, "password" to user.password))
            userDao.deleteAllUsers()
            userDao.insertUser(UserEntity(0, user.name, user.email, user.password))
            return UserModel(userEntity.name, userEntity.email, userEntity.password)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun saveUser(user: UserModel): UserModel {
        try {
            val userList: List<UserEntity> = userApi.getUsers()
            userList.forEach {
                if (it.email == user.email) {
                    val userEntity = userApi.updateUser(it.id, mapOf("name" to user.name, "email" to user.email, "password" to user.password))
                    userDao.updateUser(user.name, user.email, user.password)
                    return UserModel(userEntity.name, userEntity.email, userEntity.password)
                }
            }
            return user
        } catch (e: Exception) {
            throw e
        }
    }
}