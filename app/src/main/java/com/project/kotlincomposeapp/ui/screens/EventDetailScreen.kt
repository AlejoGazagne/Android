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
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.project.kotlincomposeapp.data.model.Event
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.components.Spacer

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
    val event: Event = Event.getEventById(eventId)

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

        // Botón para adquirir tickets
        Button(
            onClick = { /* Lógica para adquirir tickets */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(contentColor = contentColorFor(MaterialTheme.colorScheme.primary))
        ) {
            Text(text = "Adquirir Tickets", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar a favoritos
        OutlinedButton(
            onClick = { /* Lógica para agregar a favoritos */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Agregar a Favoritos", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
        }
    }
}
