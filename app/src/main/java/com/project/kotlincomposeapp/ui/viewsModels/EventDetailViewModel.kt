package com.project.kotlincomposeapp.ui.viewsModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.data.local.entity.EventEntity
import com.project.kotlincomposeapp.domain.usecase.GetEventByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val GetEventByTitleUseCase: GetEventByTitleUseCase
): ViewModel() {
//    // El evento ahora es un MutableState para que Compose pueda observar cambios
//    private val _event = mutableStateOf<EventEntity?>(null)
//    val event: State<EventEntity?> = _event
//
//    fun loadEvent(eventId: Number) {
//        val fetchedEvent = EventRepository.getEventById(eventId)
//        _event.value = fetchedEvent
//    }
//
//    fun toggleFavorite(eventId: Number) {
//        // Cambiamos el estado del favorito
//        _event.value?.let { event ->
//            // Simulamos la actualización en el repositorio
//            EventRepository.setFavorite(eventId)
//            _event.value = event.copy(isFavorite = !event.isFavorite) // Aquí aseguramos que el estado se actualice inmediatamente
//        }
//    }
}


