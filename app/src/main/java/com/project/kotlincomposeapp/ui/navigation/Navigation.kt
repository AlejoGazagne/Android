package com.project.kotlincomposeapp.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.ui.screens.auth.LoginScreen
import com.project.kotlincomposeapp.ui.screens.EditProfileScreen
import com.project.kotlincomposeapp.ui.screens.EventDetailScreen
import com.project.kotlincomposeapp.ui.screens.FavoriteScreen
import com.project.kotlincomposeapp.ui.screens.HomeScreen
import com.project.kotlincomposeapp.ui.screens.NotificationScreen
import com.project.kotlincomposeapp.ui.screens.ProfileScreen
import com.project.kotlincomposeapp.ui.screens.auth.RegisterScreen
import com.project.kotlincomposeapp.ui.screens.SearchScreen
import com.project.kotlincomposeapp.ui.screens.SettingsScreen
import com.project.kotlincomposeapp.ui.screens.auth.SplashScreen

@Composable
fun SetupNavigation (){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route){
        composable(route = Screen.Splash.route){
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Login.route){
            LoginScreen(navController = navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
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
            EditProfileScreen(navController = navController)
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
        composable(
            route = Screen.Settings.route,
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
        ) {
            SettingsScreen(navController = navController)

        }
        composable(route = Screen.Register.route,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(700)
                ) + fadeIn(animationSpec = tween(700))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(700)
                ) + fadeOut(animationSpec = tween(700))
            },
        ){
            RegisterScreen(navController = navController)
        }

        composable(route = Screen.Notifications.route,
            //enterTransition = {
            //slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth },
                //animationSpec = tween(700)
            //) + fadeIn(animationSpec = tween(700))
        //},
            //exitTransition = {
                //slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth },
                    //animationSpec = tween(700)
                //) + fadeOut(animationSpec = tween(700))
            //},
        ) {
            NotificationScreen(navController = navController)
        }
    }
}
