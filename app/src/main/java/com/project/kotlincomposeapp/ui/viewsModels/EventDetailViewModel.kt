package com.project.kotlincomposeapp.ui.viewsModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.kotlincomposeapp.data.local.entity.EventEntity
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.usecase.GetEventByTitleUseCase
import com.project.kotlincomposeapp.domain.usecase.ToggleFavoriteEventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val GetEventByTitleUseCase: GetEventByTitleUseCase,
    private val toggleFavoriteEventUseCase: ToggleFavoriteEventUseCase
): ViewModel() {
    private val _eventByTitleState = MutableStateFlow<Resource<EventModel>>(Resource.Loading())
    val eventByTitleState: StateFlow<Resource<EventModel>> get() = _eventByTitleState

    fun getEventByTitle(title: String) {
        viewModelScope.launch {
            GetEventByTitleUseCase(title).collect { result ->
                _eventByTitleState.value = result
            }
        }
    }

    fun toggleFavoriteEvent(event: EventModel) {
        viewModelScope.launch {
            toggleFavoriteEventUseCase.invoke(event).collect { result ->
                if(result is Resource.Success) {
                    val currentEvent = (_eventByTitleState.value as? Resource.Success)?.data
                    currentEvent?.let {
                        _eventByTitleState.value = Resource.Success(
                            it.copy(isFavorite = !it.isFavorite)
                        )
                    }
                }
            }
        }
    }
}


