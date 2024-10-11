package com.project.kotlincomposeapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterScreen(navController = rememberNavController())
}

@Composable
fun RegisterScreen(navController: NavController) {
    Text(text = "Register Screen")
}