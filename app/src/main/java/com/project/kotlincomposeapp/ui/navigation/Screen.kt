package com.project.kotlincomposeapp.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Home : Screen("home")
    object EditProfile : Screen("edit_profile")
    object EventDetail : Screen("eventDetail/{eventId}")
    object Search : Screen("search?title={title}")
    object Favorites : Screen("favorites")
    object Register : Screen("register")
    object Settings : Screen("settings")
}