package com.project.kotlincomposeapp.ui.viewsModels

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.support.v4.os.IResultReceiver._Parcel
import android.util.Patterns
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.kotlincomposeapp.App
import com.project.kotlincomposeapp.R

class EditProfileViewModel: ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private fun isValidUsername(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    fun sendNotification(context: Context){
        val notificationManager = context.getSystemService(NotificationManager::class.java)

        val notification = NotificationCompat.Builder(context, App.CHANNEL_ID)
            .setContentTitle("Titulo de la notificación")
            .setContentText("Cuerpo de la notificación")
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.logo))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(1, notification)
    }
}