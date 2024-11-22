package com.project.kotlincomposeapp.di

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.project.kotlincomposeapp.data.local.AppDatabase
import com.project.kotlincomposeapp.data.local.dao.EventDao
import com.project.kotlincomposeapp.data.local.dao.NotificationDao
import com.project.kotlincomposeapp.work.EventNotificationWorker
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class EventNotificationFactory @Inject constructor(
    private val eventDAO: EventDao,
    private val notificationDao: NotificationDao
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        Log.d("EventNotificationWorker", "Intentando crear worker: $workerClassName")
        return when (workerClassName) {
            EventNotificationWorker::class.java.name -> {
                EventNotificationWorker(
                    appContext,
                    workerParameters,
                    eventDAO,
                    notificationDao
                )
            }

            else -> null
        }
    }
}