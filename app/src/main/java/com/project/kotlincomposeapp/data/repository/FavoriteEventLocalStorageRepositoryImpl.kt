package com.project.kotlincomposeapp.data.repository

import com.project.kotlincomposeapp.data.local.dao.EventDao
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.repository.FavoriteEventLocalStorageRepository
import javax.inject.Inject

class FavoriteEventLocalStorageRepositoryImpl @Inject constructor(
    private val eventDao: EventDao
): FavoriteEventLocalStorageRepository {

    override suspend fun getFavoriteEvents(): MutableList<EventModel> {
        val events = eventDao.getFavoriteEvents()
        val eventModels = mutableListOf<EventModel>()
        events.forEach {
            eventModels.add(
                EventModel(
                    it.title,
                    it.date,
                    it.location,
                    it.image,
                    it.capacity,
                    it.organizer,
                    it.isFavorite
                )
            )
        }
        return eventModels
    }

    override suspend fun toggleFavoriteEvent(event: EventModel) {
        try {
            eventDao.saveFavoriteEvent(
                event.title,
                !event.isFavorite
            )
        } catch (e: Exception) {
            throw e
        }

    }
}