package com.project.kotlincomposeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.kotlincomposeapp.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("UPDATE user SET name = :name, password = :password WHERE email = :email")
    suspend fun updateUser(name: String, email: String, password: String)

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}