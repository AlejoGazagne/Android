package com.project.kotlincomposeapp.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

object Utils {
    fun convertNotificationTimeToMillis(notificationTime: String): Long {
        return when (notificationTime.lowercase()) {
            "1 día antes" -> TimeUnit.DAYS.toMillis(1)
            "3 dias antes" -> TimeUnit.DAYS.toMillis(3)
            "1 semana antes" -> TimeUnit.DAYS.toMillis(7)
            "2 semanas antes" -> TimeUnit.DAYS.toMillis(14)
            "3 semanas antes" -> TimeUnit.DAYS.toMillis(21)
            "1 mes antes" -> TimeUnit.DAYS.toMillis(30)
            else -> 0 // Valor predeterminado (nunca)
        }
    }

    fun convertEventDateToMillis(eventDate: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return try {
            val date = dateFormat.parse(eventDate)
            date?.time ?: 0L // Devuelve 0 si no puede parsear la fecha
        } catch (e: Exception) {
            e.printStackTrace()
            0L // Devuelve 0 en caso de error
        }
    }

    fun formatEventDate(eventDate: String): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("EEEE d 'de' MMMM", Locale("es", "ES")) // Formato en español

        return try {
            val date = inputDateFormat.parse(eventDate)
            date?.let {
                outputDateFormat.format(it)
            } ?: "Fecha inválida"
        } catch (e: Exception) {
            e.printStackTrace()
            "Fecha inválida"
        }
    }
}