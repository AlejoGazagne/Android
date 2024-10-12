package com.project.kotlincomposeapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.project.kotlincomposeapp.R

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: Int
) {
    object Home : BottomNavItem("home", Icons.Default.Home, R.string.home)
    object Favorites : BottomNavItem("favorites", Icons.Default.FavoriteBorder,R.string.favorites)
    object Notifications : BottomNavItem("notifications", Icons.Default.Notifications, R.string.notifications)
    object Profile : BottomNavItem("profile", Icons.Default.AccountCircle, R.string.profile)
}