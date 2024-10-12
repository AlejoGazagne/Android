package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.project.kotlincomposeapp.data.model.Notification
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.components.Spacer
import com.project.kotlincomposeapp.ui.viewsModels.NotificationViewModel

@Composable
fun NotificationScreen(navController: NavController) {
    val notificationsViewModel: NotificationViewModel = viewModel()
    MainScaffold(navController = navController) { innerPadding ->
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)) {
            Notifications(modifier = Modifier.fillMaxWidth(), notificationsViewModel, navController)
        }
    }
}

@Composable
fun Notifications(modifier: Modifier, notificationViewModel: NotificationViewModel, navController: NavController) {
    val notifications by notificationViewModel.notifications.observeAsState(listOf())

    LazyColumn(modifier = modifier) {
        item {
            // Utilizamos un Row para alinear el título y el botón
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tus Notificaciones",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Botón para marcar todas como leídas
                Button(
                    onClick = {
                        notificationViewModel.markAllAsRead()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .height(40.dp) // Ajustar la altura
                        .width(180.dp) // Ajustar el ancho
                ) {
                    Text(
                        text = "Marcar todas como leídas",
                        style = MaterialTheme.typography.bodySmall // Reducir tamaño de la fuente
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(notifications) { notification ->
            NotificationItem(
                notification = notification,
                onClick = {
                    notificationViewModel.markAsRead(notification)
                }
            )
            Spacer(modifier = Modifier.height(12.dp)) // Añadir un espacio entre notificaciones
        }
    }
}

@Composable
fun NotificationItem(notification: Notification, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        colors = if (!notification.isRead) {
            CardDefaults.cardColors(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        } else {
            CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
        }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = notification.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.message,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.date,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

