package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.kotlincomposeapp.data.local.entity.EventEntity
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.usecase.GetEventsUseCase
import com.project.kotlincomposeapp.domain.usecase.ToggleFavoriteEventUseCase
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

    init {
        fetchEvents()
    }

    // Función para obtener eventos desde el caso de uso
    private fun fetchEvents() {
        viewModelScope.launch {
            getEventsUseCase().collect { resource ->
                _eventsState.value = resource
            }
        }
    }

    // Función para actualizar el estado de favorito de un evento
    fun toggleFavoriteEvent(event: EventModel) {
        viewModelScope.launch {
            toggleFavoriteEventUseCase.invoke(event)
        }
    }
}


