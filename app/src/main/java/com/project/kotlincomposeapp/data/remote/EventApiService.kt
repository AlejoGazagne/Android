package com.project.kotlincomposeapp.data.remote

import com.project.kotlincomposeapp.data.local.entity.EventEntity
import retrofit2.http.GET

interface EventApiService {
    @GET("events")
    suspend fun getEvents(): List<EventEntity>
}