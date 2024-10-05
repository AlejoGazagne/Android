package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.model.Event
import com.project.kotlincomposeapp.data.repository.EventRepository

class FavoriteViewModel: ViewModel() {
    private val repository = EventRepository
    private val _favorites = MutableLiveData(repository.getFavorites())
    val favorites: LiveData<List<Event>> = _favorites

    fun toggleFavorite(eventId: Number) {
        // Modifica la lista de eventos
        val updatedEvents = _favorites.value?.map { event ->
            if (event.id == eventId) {
                event.copy(isFavorite = !event.isFavorite)  // Alterna el estado de favorito
            } else {
                event
            }
        }

        // Actualiza la lista de eventos
        _favorites.value = updatedEvents

        // Actualiza el repositorio
        repository.setFavorite(eventId)
    }
}
