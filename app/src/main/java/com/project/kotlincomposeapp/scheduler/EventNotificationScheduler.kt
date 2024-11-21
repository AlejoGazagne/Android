package com.project.kotlincomposeapp.scheduler

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.project.kotlincomposeapp.work.EventNotificationWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import java.util.concurrent.TimeUnit

class EventNotificationScheduler  @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun scheduleNotifications() {
        Log.d("EventNotificationWorker", "scheduleNotifications")
        val workRequest = PeriodicWorkRequestBuilder<EventNotificationWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .build()
            )
            .build()
        Log.d("EventNotificationWorker", "Encolando trabajo")
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "EventNotificationWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun scheduleImmediateCheck() {
        val workRequest = OneTimeWorkRequestBuilder<EventNotificationWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            "UniqueEventNotificationWork",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }
}