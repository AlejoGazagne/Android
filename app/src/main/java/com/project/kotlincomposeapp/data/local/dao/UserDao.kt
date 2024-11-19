package com.project.kotlincomposeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.project.kotlincomposeapp.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUser(email: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}