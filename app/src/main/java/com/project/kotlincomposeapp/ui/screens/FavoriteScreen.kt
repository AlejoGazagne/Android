package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.ui.components.EventItem
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.components.Spacer
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.FavoriteViewModel

@Composable
fun FavoriteScreen(navController: NavController) {
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    MainScaffold(navController = navController) { innerPadding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Favorite(modifier = Modifier.fillMaxWidth(), favoriteViewModel, navController)
        }
    }
}

@Composable
fun Favorite(
    modifier: Modifier,
    favoriteViewModel: FavoriteViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        favoriteViewModel.getFavoriteEvents()
    }

    val favoritesState by favoriteViewModel.favoriteEvents.collectAsState()

    when (favoritesState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(modifier), // Combina el modificador externo con el Box
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val favorites = (favoritesState as Resource.Success<MutableList<EventModel>>).data

            LazyColumn(modifier = modifier) {
                item {
                    Text(
                        text = stringResource(id = R.string.favorite),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                favorites?.let {
                    items(it.toList()) { event ->
                        EventItem(
                            event = event,
                            onFavoriteClick = {
                                favoriteViewModel.toggleFavoriteEvent(event)
                            },
                            onClick = {
                                navController.navigate(
                                    Screen.EventDetail.route.replace(
                                        "{eventTitle}",
                                        event.title
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
        is Resource.Error -> {
            val errorMessage = (favoritesState as Resource.Error).message

            Text(text = "Error: $errorMessage")
        }
    }
}

