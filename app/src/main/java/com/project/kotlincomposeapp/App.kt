package com.project.kotlincomposeapp

import android.app.Application
import androidx.work.Configuration
import com.project.kotlincomposeapp.di.EventNotificationFactory
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: EventNotificationFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}