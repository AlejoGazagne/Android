package com.project.kotlincomposeapp.ui.screens.auth

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen(navController = rememberNavController())
}

@Composable
fun SplashScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(true) }
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState = loginViewModel.loginState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            loginViewModel.earlyLogin()
        }
        delay(3000)
        isVisible = false
    }
    LaunchedEffect(loginState.value) {
        when (val state = loginState.value) {
            is Resource.Success -> {
                Log.d("Resource", "login automatico exitoso")
                isVisible = false
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
            is Resource.Error -> {
                Log.e("Resource", "login automatico fallido")
                isVisible = false
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
            is Resource.Loading -> {
                // Opcional: manejar un estado de carga adicional
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    )
    {
        AnimatedVisibility(
            visible = isVisible,
            //enter = fadeIn(initialAlpha = 0.3f),
            exit = fadeOut(targetAlpha = 0f) + slideOutVertically(targetOffsetY = { it / 20 })
        ) {
            Splash(modifier = Modifier.fillMaxSize())
        }
    }

}

@Composable
fun Splash(modifier: Modifier) {
    Column(
        modifier = modifier.size(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.logo
            ),
            contentDescription = "Company",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}