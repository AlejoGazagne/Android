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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.project.kotlincomposeapp.ui.components.EventItem
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.components.SearchBar
import com.project.kotlincomposeapp.ui.components.Spacer
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.HomeViewModel


@Composable
fun HomeScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = viewModel()
    MainScaffold(navController = navController) { innerPadding ->
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)) {
            Home(modifier = Modifier.fillMaxWidth(), homeViewModel, navController)
        }
    }
}

@Composable
fun Home(modifier: Modifier, homeViewModel: HomeViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val events by homeViewModel.events.observeAsState(listOf())

    LazyColumn(modifier = modifier) {
        item {
            SearchBar(searchQuery) { query ->
                searchQuery = query
                navController.navigate(Screen.Search.route.replace("{title}", query.text))
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Eventos populares",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(events) { event ->
            EventItem(event) { selectedEvent ->
                navController.navigate(Screen.EventDetail.route.replace("{eventId}", selectedEvent.toString()))
            }
        }
    }
}


