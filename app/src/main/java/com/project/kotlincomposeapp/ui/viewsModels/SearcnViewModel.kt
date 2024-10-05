package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.model.Event

class SearchViewModel : ViewModel() {

    fun getFilteredEvents(title:String): List<Event>{
        return Event.getFilteredEvents(title)
    }

}