package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.project.kotlincomposeapp.data.model.Event
import com.project.kotlincomposeapp.data.repository.EventRepository
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.components.Spacer
import com.project.kotlincomposeapp.ui.viewsModels.EventDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun EventDetailScreen(eventId: Number, navController: NavHostController) {
    MainScaffold(navController = navController) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            EventDetail(eventId = eventId)
        }
    }
}

@Composable
fun EventDetail(eventId: Number) {
    val detailViewModel: EventDetailViewModel = viewModel()
    val event: Event = EventRepository.getEventById(eventId)

    // Crear el estado del Snackbar y el CoroutineScope
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagen destacada
        Image(
            painter = rememberAsyncImagePainter(event.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del evento
        Text(
            text = event.name,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Detalles del evento
        Text(text = "Fecha: ${event.date}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Capacidad: ${event.capacity}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Ubicación: ${event.location}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Organizador: ${event.organizer}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))

        // Verificamos si el evento es favorito o no
        if (event.isFavorite) {
            // Botón para eliminar de favoritos
            OutlinedButton(
                onClick = {
                    detailViewModel.toggleFavorite(eventId)

                    // Mostrar Snackbar
                    scope.launch {
                        snackbarHostState.showSnackbar("Evento eliminado de favoritos")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Eliminar de Favoritos", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
            }
        } else {
            // Botón para agregar a favoritos
            OutlinedButton(
                onClick = {
                    detailViewModel.toggleFavorite(eventId)

                    // Mostrar Snackbar
                    scope.launch {
                        snackbarHostState.showSnackbar("Evento agregado a favoritos")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Agregar a Favoritos", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SnackbarHost para mostrar el Snackbar
        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}

