package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.ui.components.BackBar
import com.project.kotlincomposeapp.ui.components.Spacer
import com.project.kotlincomposeapp.ui.viewsModels.EventDetailViewModel
import kotlinx.coroutines.launch

@Composable
fun EventDetailScreen(eventId: Number, navController: NavHostController) {
    BackBar(
        modifier = Modifier,
        navController,
        stringResource(R.string.event),
        "home"
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            EventDetail(eventId = eventId)
        }
    }
}

@Composable
fun EventDetail(eventId: Number) {
    // TODO
//    val detailViewModel: EventDetailViewModel = hiltViewModel()
//    val event by detailViewModel.event
//    val snackbarHostState = remember { SnackbarHostState() }
//    val scope = rememberCoroutineScope()
//
//    detailViewModel.loadEvent(eventId)
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // Verificamos que el evento no sea nulo
//        event?.let { event ->
//
//            Image(
//                painter = rememberAsyncImagePainter(event.image),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
//                contentScale = ContentScale.Crop
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Nombre del evento
//            Text(
//                text = event.name,
//                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
//                color = MaterialTheme.colorScheme.primary
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            // Detalles del evento
//            Text(text = "${stringResource(id = R.string.date)}: ${event.date}", style = MaterialTheme.typography.bodyLarge)
//            Text(text = "${stringResource(id = R.string.capacity)}: ${event.capacity}", style = MaterialTheme.typography.bodyLarge)
//            Text(text = "${stringResource(id = R.string.ubication)}: ${event.location}", style = MaterialTheme.typography.bodyLarge)
//            Text(text = "${stringResource(id = R.string.organizer)}: ${event.organizer}", style = MaterialTheme.typography.bodyLarge)
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Verificamos si el evento es favorito o no
//            if (event.isFavorite) {
//                OutlinedButton(
//                    onClick = {
//                        detailViewModel.toggleFavorite(eventId)
//
//                        scope.launch {
//                            snackbarHostState.showSnackbar("Evento eliminado de favoritos", duration = SnackbarDuration.Short)
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp),
//                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
//                ) {
//                     Text(text = stringResource(id = R.string.delete_favorite), style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
//                }
//            } else {
//                OutlinedButton(
//                    onClick = {
//                        detailViewModel.toggleFavorite(eventId)
//
//                        scope.launch {
//                            snackbarHostState.showSnackbar("Evento agregado a favoritos", duration = SnackbarDuration.Short)
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp),
//                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
//                ) {
//                    Text(text = stringResource(id = R.string.add_favorite), style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
//                }
//
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            SnackbarHost(
//                hostState = snackbarHostState,
//                snackbar = { snackbarData ->
//                    Snackbar(
//                        snackbarData = snackbarData,
//                        containerColor = MaterialTheme.colorScheme.primary
//                    )
//                }
//            )
//        }
//    }
}

