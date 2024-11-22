package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.usecase.events.GetEventsUseCase
import com.project.kotlincomposeapp.domain.usecase.favorite.ToggleFavoriteEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val toggleFavoriteEventUseCase: ToggleFavoriteEventUseCase
) : ViewModel() {

    // Estado expuesto para la UI
    private val _eventsState = MutableStateFlow<Resource<MutableList<EventModel>>>(Resource.Loading())
    val eventsState: StateFlow<Resource<MutableList<EventModel>>> = _eventsState

    // Función para obtener eventos desde el caso de uso
    fun fetchEvents() {
        viewModelScope.launch {
            getEventsUseCase().collect { resource ->
                _eventsState.value = resource
            }
        }
    }

    fun toggleFavoriteEvent(event: EventModel) {
        viewModelScope.launch {
            toggleFavoriteEventUseCase.invoke(event).collect { result ->
                if (result is Resource.Success) {
                    val updatedList = (_eventsState.value as? Resource.Success<MutableList<EventModel>>)?.data?.map {
                        if (it.title == event.title) {
                            it.copy(isFavorite = !it.isFavorite)
                        } else it
                    }?.toMutableList()

                    // Emite la nueva lista al estado
                    _eventsState.value = Resource.Success(updatedList ?: mutableListOf())
                }
            }
        }
    }
}


