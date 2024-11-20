package com.project.kotlincomposeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.kotlincomposeapp.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    suspend fun getEvents(): MutableList<EventEntity>

    @Query("SELECT * FROM events WHERE isFavorite = 1")
    suspend fun getFavoriteEvents(): MutableList<EventEntity>

    @Query("SELECT * FROM events WHERE title LIKE '%' || :tittle || '%'")
    suspend fun getEventByTitle(tittle: String): EventEntity

    @Query("SELECT * FROM events WHERE location LIKE '%' || :location || '%'")
    suspend fun getEventByLocation(location: String): MutableList<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveEvents(events: List<EventEntity>)

    @Query("UPDATE events SET isFavorite = :isFavorite WHERE title = :title")
    suspend fun saveFavoriteEvent(title: String, isFavorite: Boolean)
}