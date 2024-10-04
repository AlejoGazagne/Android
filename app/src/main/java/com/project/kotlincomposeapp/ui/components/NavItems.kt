package com.project.kotlincomposeapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Wallet : BottomNavItem("favorites", Icons.Default.FavoriteBorder, "Favorites")
    object Notifications : BottomNavItem("tickets", Icons.Default.ShoppingCart, "Tickets")
    object Account : BottomNavItem("profile", Icons.Default.AccountCircle, "Profile")
}