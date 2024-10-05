package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.project.kotlincomposeapp.ui.components.EventItem
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.components.Spacer
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.FavoriteViewModel

@Composable
fun FavoriteScreen(navController: NavController) {
    val favoriteViewModel: FavoriteViewModel = viewModel()
    MainScaffold(navController = navController) { innerPadding ->
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)) {
            Favorite(modifier = Modifier.fillMaxWidth(), favoriteViewModel, navController)
        }
    }
}

@Composable
fun Favorite(modifier: Modifier, favoriteViewModel: FavoriteViewModel, navController: NavController) {
    val favorites by favoriteViewModel.favorites.observeAsState(listOf())
    LazyColumn(modifier = modifier) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tus Favoritos",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(favorites) { event ->
            EventItem(
                event = event,
                onFavoriteClick = { eventId ->
                    favoriteViewModel.toggleFavorite(eventId)
                },
                onClick = { selectedEvent ->
                    navController.navigate(Screen.EventDetail.route.replace("{eventId}", selectedEvent.toString()))
                }
            )
        }
    }
}
