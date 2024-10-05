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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.kotlincomposeapp.ui.components.EventItem
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.components.SearchBar
import com.project.kotlincomposeapp.ui.components.Spacer
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.SearchViewModel


@Composable
fun SearchScreen(navController: NavController, title: String) {

    MainScaffold(navController = navController) { innerPadding ->
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)) {
            Search(modifier = Modifier.fillMaxWidth(), navController, title)
        }

    }
}

@Composable
fun Search(modifier: Modifier, navController: NavController, title: String) {
    var searchQuery by remember { mutableStateOf(TextFieldValue(title)) }
    val filteredEvents = SearchViewModel().getFilteredEvents(searchQuery.text)

    LazyColumn(modifier = modifier) {
        item {
            SearchBar(searchQuery) { query ->
                searchQuery = query
                if (searchQuery.text == ""){
                    navController.navigate(Screen.Home.route){
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Resultado de tu busqueda",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(filteredEvents) { event ->
            EventItem(event) { selectedEvent ->
                navController.navigate(Screen.EventDetail.route.replace("{eventId}", selectedEvent.toString()))
            }
        }
    }
}