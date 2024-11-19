package com.project.kotlincomposeapp.domain.model

data class EventModel(
    val title: String,
    val date: String,
    val location: String,
    val image: String,
    val capacity: Int,
    val organizer: String,
    var isFavorite: Boolean = false
)
