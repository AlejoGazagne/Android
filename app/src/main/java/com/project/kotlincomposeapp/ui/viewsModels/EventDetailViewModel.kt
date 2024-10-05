package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.repository.EventRepository

class EventDetailViewModel : ViewModel() {
    private val repository = EventRepository

    fun toggleFavorite(eventId: Number) {
        repository.setFavorite(eventId)
    }
}