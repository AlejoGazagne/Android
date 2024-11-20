package com.project.kotlincomposeapp.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.kotlincomposeapp.ui.navigation.SetupNavigation
import com.project.kotlincomposeapp.ui.theme.MyAppTheme
import com.project.kotlincomposeapp.ui.viewsModels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            val windowSize = calculateWindowSizeClass(this)
            LoadAppConfigurations()
            MyAppTheme (
                windowSize = windowSize.widthSizeClass
            ){
                val isDarkTheme = isSystemInDarkTheme()

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

    @Composable
    private fun LoadAppConfigurations(viewModel: SettingsViewModel = viewModel()) {
        val context = LocalContext.current

        // Llamar a la función de carga de preferencias

        viewModel.loadPreferences(context)

        val sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        // Cargar el idioma guardado
        val languageCode = sharedPreferences.getString("language", Locale.getDefault().language)
            ?: Locale.getDefault().language
        val locale = Locale(languageCode)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // agregar más configuraciones para el inicio
    }
}