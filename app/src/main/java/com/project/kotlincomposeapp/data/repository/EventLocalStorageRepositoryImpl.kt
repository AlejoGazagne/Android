package com.project.kotlincomposeapp.data.repository

import com.project.kotlincomposeapp.data.local.dao.EventDao
import com.project.kotlincomposeapp.data.remote.EventApiService
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.repository.EventLocalStorageRepository
import javax.inject.Inject

class EventLocalStorageRepositoryImpl @Inject constructor(
    private val eventDao: EventDao,
    private val eventApi: EventApiService,
) : EventLocalStorageRepository {

    override suspend fun getEvents(): MutableList<EventModel> {
        // Obtén los eventos de la API y los eventos existentes en la base de datos
        val apiEvents = eventApi.getEvents()
        val dbEvents = eventDao.getEvents().associateBy { it.id }

        // Mapear eventos de la API, preservando los favoritos de la base de datos
        val eventEntities = apiEvents.map { apiEvent ->
            apiEvent.copy(isFavorite = dbEvents[apiEvent.id]?.isFavorite ?: false)
        }

        // Guarda los eventos actualizados en la base de datos
        eventDao.saveEvents(eventEntities)

        // Convierte las entidades de la base de datos en modelos de aplicación
        return eventDao.getEvents().map { eventEntity ->
            EventModel(
                title = eventEntity.title,
                date = eventEntity.date,
                location = eventEntity.location,
                image = eventEntity.image,
                capacity = eventEntity.capacity,
                organizer = eventEntity.organizer,
                isFavorite = eventEntity.isFavorite
            )
        }.toMutableList()
    }


    override suspend fun getEventByTitle(title: String): EventModel {
        val event = eventDao.getEventByTitle(title)
        return EventModel(
            event.title,
            event.date,
            event.location,
            event.image,
            event.capacity,
            event.organizer,
            event.isFavorite
        )
    }

    override suspend fun getEventsByLocation(location: String): MutableList<EventModel> {
        val events = eventDao.getEventByLocation(location)
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
}