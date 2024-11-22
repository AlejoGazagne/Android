package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.usecase.favorite.GetFavoriteEventsUseCase
import com.project.kotlincomposeapp.domain.usecase.favorite.ToggleFavoriteEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoriteEventsUseCase,
    private val toggleFavoriteEventUseCase: ToggleFavoriteEventUseCase
) : ViewModel() {

    private val _favoriteEvents =
        MutableStateFlow<Resource<MutableList<EventModel>>>(Resource.Loading())
    val favoriteEvents: StateFlow<Resource<MutableList<EventModel>>> get() = _favoriteEvents

    fun getFavoriteEvents() {
        viewModelScope.launch {
            getFavoritesUseCase().collect() { result ->
                _favoriteEvents.value = result
            }
        }
    }

    fun toggleFavoriteEvent(event: EventModel) {
        viewModelScope.launch {
            toggleFavoriteEventUseCase.invoke(event).collect { result ->
                if (result is Resource.Success) {
                    // Actualiza la lista localmente
                    val updatedList = (_favoriteEvents.value as? Resource.Success<MutableList<EventModel>>)?.data?.map {
                        if (it.title == event.title) {
                            it.copy(isFavorite = !it.isFavorite) // Cambia solo el estado de favorito
                        } else it
                    }?.toMutableList()

                    // Emite la nueva lista al estado
                    _favoriteEvents.value = Resource.Success(updatedList ?: mutableListOf())
                }
            }
        }
    }
}