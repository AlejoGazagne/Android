package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.domain.model.NotificationModel
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.components.Spacer
import com.project.kotlincomposeapp.ui.viewsModels.NotificationsViewModel
import kotlin.random.Random

@Composable
fun NotificationScreen(navController: NavController) {
    val notificationsViewModel: NotificationsViewModel = hiltViewModel()

    MainScaffold(navController = navController) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.notifications),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                NotificationsList(
                    notificationsViewModel = notificationsViewModel
                )
            }

            MarkAllAsReadButton(
                onMarkAllAsRead = { notificationsViewModel.markAllNotificationsAsRead() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun NotificationsList(notificationsViewModel: NotificationsViewModel) {
    val notificationState by notificationsViewModel.stateNotifications.collectAsState()

    when (notificationState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {

            val notifications =
                (notificationState as Resource.Success<MutableList<NotificationModel>>).data

            notifications?.let {
                LazyColumn {
                    items(notifications, key = { it.title + Random(0) }) { notification ->

                        NotificationItem(
                            notification = notification,
                            notificationsViewModel
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    item {
                        Spacer(modifier = Modifier.height(64.dp))
                    }
                }
            }
        }

        is Resource.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${(notificationState as Resource.Error).message}",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

    }
}

@Composable
fun NotificationItem(
    notification: NotificationModel,
    notificationsViewModel: NotificationsViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            if (!notification.isRead) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            else MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = notification.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.message,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.date,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (!notification.isRead) {
                Button(
                    onClick = { notificationsViewModel.markNotificationAsRead(notification) },
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Text(text = stringResource(id = R.string.mark_as_read))
                }
            }
        }
    }
}

@Composable
fun MarkAllAsReadButton(
    onMarkAllAsRead: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onMarkAllAsRead,
        modifier = modifier.height(48.dp),
        border = BorderStroke(1.dp, Color.Black),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = stringResource(id = R.string.all_notifications_read),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}