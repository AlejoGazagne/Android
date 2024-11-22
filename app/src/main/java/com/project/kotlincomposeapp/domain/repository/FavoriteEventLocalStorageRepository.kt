package com.project.kotlincomposeapp.domain.repository

import com.project.kotlincomposeapp.domain.model.EventModel

interface FavoriteEventLocalStorageRepository {

    suspend fun getFavoriteEvents(): MutableList<EventModel>

    suspend fun toggleFavoriteEvent(event: EventModel)

}