package com.project.kotlincomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.project.kotlincomposeapp.ui.navigation.SetupNavigation
import com.project.kotlincomposeapp.ui.theme.MyAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            MyAppTheme {
                val isDarkTheme = isSystemInDarkTheme()

                // Cambia el color de la barra de estado y la barra de navegación
                val statusBarColor = if (isDarkTheme) {
                    MaterialTheme.colorScheme.background
                } else {
                    MaterialTheme.colorScheme.background
                }

                window.statusBarColor = statusBarColor.toArgb()
                window.navigationBarColor = statusBarColor.toArgb()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SetupNavigation()
                }
            }
        }
    }
}