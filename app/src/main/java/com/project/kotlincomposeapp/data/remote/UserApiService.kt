package com.project.kotlincomposeapp.data.remote

import com.project.kotlincomposeapp.data.local.entity.UserEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @GET("users")
    suspend fun getUserByEmailAndPassword(@Query("email") email: String, @Query("password") password: String): List<UserEntity>

    @GET("users")
    suspend fun getUsers(): List<UserEntity>

    @PATCH("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body updatedUser: Map<String, String>): UserEntity

    @POST("users")
    suspend fun createUser(@Body newUser: Map<String, String>): UserEntity
}