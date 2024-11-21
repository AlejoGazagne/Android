package com.project.kotlincomposeapp.work

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.data.local.AppDatabase
import com.project.kotlincomposeapp.data.local.dao.EventDao
import com.project.kotlincomposeapp.data.local.entity.EventEntity
import com.project.kotlincomposeapp.utils.Utils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class EventNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val eventDao: EventDao
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val CHANNEL_ID = "event_channel"
    }

    override suspend fun doWork(): Result {
        createNotificationChannel()

        val sharedPreferences = applicationContext.getSharedPreferences("notification", Context.MODE_PRIVATE)
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        val notificationTimeBefore = sharedPreferences.getString("selectedTime", "0")
            ?.let { Utils.convertNotificationTimeToMillis(it) }

        return withContext(Dispatchers.IO) {
            try {
                val events = eventDao.getFavoriteEvents()
                val currentTime = System.currentTimeMillis()

                events.forEach { event ->
                    val eventTime = Utils.convertEventDateToMillis(event.date)

                    if (notificationTimeBefore != null && notificationTimeBefore != 0L &&
                        (eventTime - notificationTimeBefore <= currentTime && eventTime > currentTime)) {
                        sendNotification(event, notificationManager)
                    }
                }
                Result.success()
            } catch (e: Exception) {
                Log.e("EventNotificationWorker", "Error ejecutando Worker", e)
                Result.failure()
            }
        }
    }

    private fun sendNotification(event: EventEntity, notificationManager: NotificationManagerCompat) {
        val channelId = "event_channel"

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Evento próximo")
            .setContentText("${event.title} se llevará a cabo el ${Utils.formatEventDate(event.date)}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(event.id, notification)
        } else {
            Log.e("EventNotificationWorker", "Permiso para notificaciones no otorgado.")
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = CHANNEL_ID
            val name = "Eventos"
            val description = "Notificaciones para eventos próximos"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, name, importance).apply {
                this.description = description
            }

            val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
