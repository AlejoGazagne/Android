package com.project.kotlincomposeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.kotlincomposeapp.ui.navigation.BottomNavItem
import com.project.kotlincomposeapp.ui.navigation.Screen

/*@Preview(showBackground = true)
@Composable
fun PreviewBottomNavBar() {
    BottomNavBar(navController = rememberNavController())
}*/

@Composable
fun MainScaffold(navController: NavController, content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        bottomBar = {
            BottomNavBar(navController, unreadNotifications = true)
        },
        content = content
    )
}

@Composable
fun BottomNavBar(navController: NavController, unreadNotifications: Boolean) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Wallet,
        BottomNavItem.Notifications,
        BottomNavItem.Account
    )

    NavigationBar(
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    // Si es el ícono de notificaciones, agregar el punto rojo si hay notificaciones no leídas
                    if (item is BottomNavItem.Notifications) {
                        Box {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                            if (unreadNotifications) {
                                // Dibuja el punto rojo en la esquina superior derecha del ícono
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(Color.Red, shape = CircleShape)
                                        .align(Alignment.TopEnd)
                                        .offset(x = 6.dp, y = (-6).dp) // Ajusta el posicionamiento del punto rojo
                                )
                            }
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    }
                },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Evitar múltiples copias del mismo destino en la pila
                        launchSingleTop = true
                        // Restaurar la pila del backstack en la navegación
                        restoreState = true
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
    }
}
