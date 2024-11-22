package com.project.kotlincomposeapp.domain.repository

import com.project.kotlincomposeapp.domain.model.EventModel

interface EventLocalStorageRepository {

    suspend fun getEvents(): MutableList<EventModel>

    suspend fun getEventByTitle(title: String): EventModel

    suspend fun getEventsByLocation(location: String): MutableList<EventModel>

}