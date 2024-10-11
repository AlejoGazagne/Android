package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Tus Notificaciones",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(notifications) { notification ->
            NotificationItem(
                notification = notification,
                onClick = {
                    notificationViewModel.markAsRead(notification)
                }
            )
        }
    }
}

@Composable
fun NotificationItem(notification: Notification, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
            .background(
                if (!notification.isRead) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
                else MaterialTheme.colorScheme.tertiary
            )
            .clip(RoundedCornerShape(10.dp))

    ) {
        Text(text = notification.title, fontWeight = FontWeight.Bold)
        Text(text = notification.message)
        Text(text = notification.date, style = MaterialTheme.typography.bodySmall)
    }
}
