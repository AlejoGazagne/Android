package com.project.kotlincomposeapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
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
            BottomNavBar(navController)
        },
        content = content
    )
}

@Composable
fun BottomNavBar(navController: NavController) {
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
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
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
