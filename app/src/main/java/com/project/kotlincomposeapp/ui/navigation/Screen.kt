package com.project.kotlincomposeapp.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Profile : Screen("profile")
    object Home : Screen("home")
    object EventDetail : Screen("eventDetail/{eventId}")
    object Search : Screen("search?title={title}")
}