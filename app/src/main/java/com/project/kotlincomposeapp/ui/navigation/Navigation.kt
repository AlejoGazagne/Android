package com.project.kotlincomposeapp.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.ui.LoginScreen
import com.project.kotlincomposeapp.ui.screens.EditProfileScreen
import com.project.kotlincomposeapp.ui.screens.EventDetailScreen
import com.project.kotlincomposeapp.ui.screens.FavoriteScreen
import com.project.kotlincomposeapp.ui.screens.HomeScreen
import com.project.kotlincomposeapp.ui.screens.ProfileScreen
import com.project.kotlincomposeapp.ui.screens.SearchScreen
import com.project.kotlincomposeapp.ui.screens.SplashScreen
import com.project.kotlincomposeapp.ui.viewsModels.SharedViewModel

@Composable
fun SetupNavigation (){
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Splash.route){
        composable(route = Screen.Splash.route){
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Login.route){
            LoginScreen(navController = navController, sharedViewModel)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController, sharedViewModel)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.EditProfile.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(500)
                ) + fadeIn(animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(500)
                ) + fadeOut(animationSpec = tween(500))
            },
        ){
            EditProfileScreen(navController = navController, sharedViewModel)
        }
        composable(route = Screen.EventDetail.route) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            EventDetailScreen(navController = navController, eventId = eventId!!.toInt())
        }
        composable(route = Screen.Search.route) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")
            SearchScreen(navController = navController, title = title!!)

        }
        composable(route = Screen.Favorites.route) {
            FavoriteScreen(navController = navController)
        }
    }
}
