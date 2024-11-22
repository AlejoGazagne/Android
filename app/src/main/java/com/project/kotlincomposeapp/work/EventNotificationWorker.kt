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
import com.project.kotlincomposeapp.data.local.dao.EventDao
import com.project.kotlincomposeapp.data.local.dao.NotificationDao
import com.project.kotlincomposeapp.data.local.entity.EventEntity
import com.project.kotlincomposeapp.data.local.entity.NotificationEntity
import com.project.kotlincomposeapp.utils.Utils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@HiltWorker
class EventNotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val eventDao: EventDao,
    private val notificationDao: NotificationDao
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val CHANNEL_ID = "event_channel"
        const val CHANNEL_NAME = "Eventos"
        const val CHANNEL_DESCRIPTION = "Notificaciones para eventos próximos"
    }

    override suspend fun doWork(): Result {
        Log.d("EventNotificationWorker", "Ejecutando Worker")
        createNotificationChannel()

        val sharedPreferences =
            applicationContext.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        val notificationTimeBefore = sharedPreferences.getString("selectedTime", "1 Semana antes")
            ?.let { Utils.convertNotificationTimeToMillis(it) }

        return withContext(Dispatchers.IO) {
            try {
                val events = eventDao.getFavoriteEvents()
                val currentTime = System.currentTimeMillis()

                events.forEach { event ->
                    val eventTime = Utils.convertEventDateToMillis(event.date)
                    Log.d(
                        "EventNotificationWorker",
                        "Evento: ${event.title}, Tiempo Actual: $currentTime, Tiempo de notificacion: $notificationTimeBefore Tiempo Evento: $eventTime"
                    )
                    if (notificationTimeBefore != null && notificationTimeBefore != 0L &&
                        (eventTime - notificationTimeBefore <= currentTime && eventTime > currentTime)
                    ) {
                        Log.d(
                            "EventNotificationWorker",
                            "Enviando notificación para evento: ${event.title}"
                        )
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


    private suspend fun sendNotification(
        event: EventEntity,
        notificationManager: NotificationManagerCompat
    ) {
        val channelId = CHANNEL_ID

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

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val currentDate = dateFormat.format(Date())

            val notificationEntity = NotificationEntity(
                id = 0, // El ID se autogenera
                title = "Evento próximo: ${event.title}",
                message = "${event.title} se llevará a cabo el ${Utils.formatEventDate(event.date)}",
                date = currentDate,
                isRead = false,
                isDeleted = false
            )
            notificationDao.saveNotification(notificationEntity)
        } else {
            Log.e("EventNotificationWorker", "Permiso para notificaciones no otorgado.")
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = CHANNEL_ID
            val name = CHANNEL_NAME
            val description = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(channelId, name, importance).apply {
                this.description = description
            }
            Log.d("EventNotificationWorker", "Creando canal de notificaciones")
            val notificationManager =
                applicationContext.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}
