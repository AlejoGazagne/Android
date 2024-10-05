package com.project.kotlincomposeapp.data.model

data class Event(
    val id: Number,
    val name: String,
    val date: String,
    val location: String,
    val image: String,
    val capacity: Int,
    val organizer: String,
    var isFavorite: Boolean // ahora es var para poder modificarlo
)



