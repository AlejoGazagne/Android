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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.ui.components.EventItem
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.components.SearchBar
import com.project.kotlincomposeapp.ui.components.Spacer
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.HomeViewModel


@Composable
fun HomeScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    MainScaffold(navController = navController) { innerPadding ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Home(modifier = Modifier.fillMaxWidth(), homeViewModel, navController)
        }
    }
}

@Composable
fun Home(modifier: Modifier, homeViewModel: HomeViewModel, navController: NavController) {

    LaunchedEffect(Unit) {
        homeViewModel.fetchEvents()
    }

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val eventsState by homeViewModel.eventsState.collectAsState()

    when (eventsState) {
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
            val events = (eventsState as Resource.Success<MutableList<EventModel>>).data

            val filteredEvents = events?.filter { event ->
                event.title.contains(searchQuery.text, ignoreCase = true)
            }

            // Obtener eventos faltantes
            val remainingEvents = events?.filterNot { event ->
                event.title.contains(searchQuery.text, ignoreCase = true)
            }

            LazyColumn(modifier = modifier) {
                item {
                    SearchBar(searchQuery) { query ->
                        searchQuery = query
                        //navController.navigate(Screen.Search.route.replace("{title}", query.text))
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.popular_events),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                filteredEvents?.let {
                    items(it.toList()) { event ->
                        EventItem(
                            event = event,
                            onFavoriteClick = {
                                homeViewModel.toggleFavoriteEvent(event)
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
                // Mostrar eventos faltantes
                remainingEvents?.let {
                    items(it) { event ->
                        EventItem(
                            event = event,
                            onFavoriteClick = {
                                homeViewModel.toggleFavoriteEvent(event)
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
                if (filteredEvents != null) {
                    if (filteredEvents.isEmpty()) {
                        items(events.toList()) { event ->
                            EventItem(
                                event = event,
                                onFavoriteClick = {
                                    homeViewModel.toggleFavoriteEvent(event)
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
        }
        is Resource.Error -> {
            val errorMessage = (eventsState as Resource.Error).message

            Text(text = "Error: $errorMessage")
        }

    }
}


