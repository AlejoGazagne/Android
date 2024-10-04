package com.project.kotlincomposeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.ui.LoginScreen
import com.project.kotlincomposeapp.ui.screens.HomeScreen
import com.project.kotlincomposeapp.ui.screens.ProfileScreen
import com.project.kotlincomposeapp.ui.screens.SplashScreen

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
        composable(route = Screen.Profile.route){
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}
