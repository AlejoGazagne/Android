package com.project.kotlincomposeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
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

private val LocalDimens = staticCompositionLocalOf { DefaultDimens }

@Composable
fun ProvideDimens(
    dimens: Dimens,
    content: @Composable () -> Unit
){
    val dimensionSet = remember { dimens }
    CompositionLocalProvider(LocalDimens provides dimensionSet, content = content)
}

@Composable
fun MyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    windowSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    val dimensions = if (windowSize > WindowWidthSizeClass.Compact) TabletDimens else DefaultDimens

    ProvideDimens(dimens = dimensions){
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = content
        )
    }
}

object MyAppTheme {
    val dimens: Dimens
        @Composable
        @ReadOnlyComposable
        get() = LocalDimens.current
}