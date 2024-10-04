package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.model.Event

class HomeViewModel : ViewModel() {
    private val _events = MutableLiveData(Event.getEvents())
    val events: LiveData<List<Event>> = _events

    fun searchEvents(query: String) {
        // Lógica para filtrar eventos según el query
        _events.value = _events.value?.filter { it.name.contains(query, ignoreCase = true) }
    }
}
