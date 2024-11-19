package com.project.kotlincomposeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.project.kotlincomposeapp.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    suspend fun getEvents(): MutableList<EventEntity>

    @Query("SELECT * FROM events WHERE isFavorite = 1")
    suspend fun getFavoriteEvents(): MutableList<EventEntity>

    @Query("SELECT * FROM events WHERE title LIKE '%' || :tittle || '%'")
    suspend fun getEventByTitle(tittle: String): MutableList<EventEntity>

    @Query("SELECT * FROM events WHERE location LIKE '%' || :location || '%'")
    suspend fun getEventByLocation(location: String): MutableList<EventEntity>

    @Query("UPDATE events SET isFavorite = :isFavorite WHERE title = :title AND date = :date AND location = :location AND image = :image AND capacity = :capacity AND organizer = :organizer")
    suspend fun saveFavoriteEvent(title: String, date: String, location: String, image: String, capacity: Int, organizer: String, isFavorite: Boolean)

    @Query("UPDATE events SET isFavorite = :isFavorite WHERE title = :title AND date = :date AND location = :location AND image = :image AND capacity = :capacity AND organizer = :organizer")
    suspend fun deleteFavoriteEvent(title: String, date: String, location: String, image: String, capacity: Int, organizer: String, isFavorite: Boolean)
}