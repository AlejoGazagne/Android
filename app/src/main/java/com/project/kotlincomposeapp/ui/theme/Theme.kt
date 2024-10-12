package com.project.kotlincomposeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.pruebacompose.ui.theme.Typography

private val LightColors = lightColorScheme(
    primary = SecondGreen,
    secondary = Dark,
    tertiary = DarkGreen,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Gray,
    onBackground = Color.DarkGray,
    onSurface = Color.DarkGray,
)

private val DarkColors = darkColorScheme(
    primary = BoneWhite,
    secondary = Dark,
    tertiary = Color.White,
    background = Dark,
    surface = Dark,
    onPrimary = Dark,
    onSecondary = BoneWhite,
    onBackground = BoneWhite,
    onSurface = BoneWhite
)

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}