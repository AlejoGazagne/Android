package com.project.kotlincomposeapp.ui.viewsModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.local.entity.EventEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(): ViewModel() {
//    private val repository = EventRepository
//    private val _filteredEvents = mutableStateListOf<EventEntity>()
//
//    fun getFilteredEvents(title: String): List<EventEntity> {
//        _filteredEvents.clear()
//        _filteredEvents.addAll(repository.getFilteredEvents(title))
//        return _filteredEvents
//    }
//
//    fun toggleFavorite(eventId: Number) {
//        val index = _filteredEvents.indexOfFirst { it.id == eventId }
//        if (index != -1) {
//            _filteredEvents[index] = _filteredEvents[index].copy(isFavorite = !_filteredEvents[index].isFavorite)
//        }
//
//        repository.setFavorite(eventId)
//    }
}