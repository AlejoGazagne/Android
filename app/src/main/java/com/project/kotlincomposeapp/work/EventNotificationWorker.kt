package com.project.kotlincomposeapp.work

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.data.local.AppDatabase
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
    private val database: AppDatabase, // Inyectamos la base de datos
    private val sharedPreferences: SharedPreferences // Inyectamos las preferencias
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("EventNotificationWorker", "doWork")
        val notificationManager = NotificationManagerCompat.from(applicationContext)
        val notificationTimeBefore =
            sharedPreferences.getString("selectedTime", System.currentTimeMillis().toString())
                ?.let { Utils.convertNotificationTimeToMillis(it) }

        return withContext(Dispatchers.IO) {
            try {
                val events = database.eventDao().getFavoriteEvents()
                Log.d("EventNotificationWorker", "Eventos favoritos: $events")
                val currentTime = System.currentTimeMillis()

                events.forEach { event ->
                    val eventTime = Utils.convertEventDateToMillis(event.date)

                    Log.d("EventNotificationWorker", "Tiempo actual: $currentTime, Tiempo del evento: $eventTime, Tiempo antes: $notificationTimeBefore")

                    if (notificationTimeBefore != null) {
                        if (notificationTimeBefore != 0L && (eventTime - notificationTimeBefore <= currentTime && eventTime > currentTime)) {
                            sendNotification(event, notificationManager)
                        } else {
                            Log.d("EventNotificationWorker", "No se tiene permiso para notificar.")
                        }
                    }
                }

                Result.success()
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure()
            }
        }
    }

    private fun sendNotification(
        event: EventEntity,
        notificationManager: NotificationManagerCompat
    ) {
        val channelId = "event_channel"
        Log.d("EventNotificationWorker", "Enviando notificación para el evento: ${event.title}")

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Evento próximo")
            .setContentText("${event.title} se llevará a cabo el ${Utils.formatEventDate(event.date)}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(event.id, notification)
        }
    }
}