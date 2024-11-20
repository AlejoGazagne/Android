package com.project.kotlincomposeapp.ui.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.domain.model.EventModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.ui.components.BackBar
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.components.Spacer
import com.project.kotlincomposeapp.ui.viewsModels.EventDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EventDetailScreen(eventTitle: String, navController: NavHostController) {
    val detailViewModel: EventDetailViewModel = hiltViewModel()
    val context = LocalContext.current

    MainScaffold(navController = navController) { innerPadding ->
        BackBar(
            modifier = Modifier,
            navController,
            stringResource(R.string.event),
            navigateTo = detailViewModel.isScreenFavoriteOrHome(detailViewModel.isScreenInBackStack(navController, "favorites")),
        ) {
            Log.e("EventDetailScreen", detailViewModel.isScreenFavoriteOrHome(detailViewModel.isScreenInBackStack(navController, "favorite")))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                EventDetail(eventTitle, detailViewModel)
            }
        }
    }
}

@Composable
fun EventDetail(eventTitle: String, detailViewModel: EventDetailViewModel) {
    LaunchedEffect(Unit) {
        detailViewModel.getEventByTitle(eventTitle)
    }

    val eventByTitleState by detailViewModel.eventByTitleState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    when (eventByTitleState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val event = (eventByTitleState as Resource.Success<EventModel>).data

            event?.let {
                EventDetailsContent(
                    event = it,
                    detailViewModel = detailViewModel,
                    snackbarHostState = snackbarHostState,
                    scope = scope
                )
            }
        }

        is Resource.Error -> {
            val errorMessage = (eventByTitleState as Resource.Error).message

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun EventDetailsContent(
    event: EventModel,
    detailViewModel: EventDetailViewModel,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = rememberAsyncImagePainter(event.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .shadow(8.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                DetailRow(label = stringResource(id = R.string.date), value = event.date)
                DetailRow(label = stringResource(id = R.string.capacity), value = event.capacity.toString())
                DetailRow(label = stringResource(id = R.string.ubication), value = event.location)
                DetailRow(label = stringResource(id = R.string.organizer), value = event.organizer)
            }
        }

        // Botón al final de la pantalla
        FavoriteButton(
            isFavorite = event.isFavorite,
            onClick = {
                detailViewModel.toggleFavoriteEvent(event)
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = if (event.isFavorite) "Evento eliminado de favoritos" else "Evento agregado a favoritos",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var buttonScale by remember { mutableStateOf(1f) }

    LaunchedEffect(buttonScale) {
        if (buttonScale > 1f) {
            delay(100)
            buttonScale = 1f
        }
    }

    OutlinedButton(
        onClick = {
            buttonScale = 1.2f
            onClick()
        },
        modifier = modifier
            .fillMaxWidth()
            .scale(buttonScale)
            .height(56.dp),
        border = BorderStroke(1.dp, if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            contentColor = if (isFavorite) Color.White else MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = if (isFavorite) stringResource(id = R.string.delete_favorite) else stringResource(id = R.string.add_favorite),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


