package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.model.Event

class HomeViewModel : ViewModel() {
    private val _events = MutableLiveData(Event.getEvents())
    val events: LiveData<List<Event>> = _events

}
