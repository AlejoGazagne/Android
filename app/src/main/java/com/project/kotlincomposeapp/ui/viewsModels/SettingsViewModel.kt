package com.project.kotlincomposeapp.ui.viewsModels

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.kotlincomposeapp.R
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    // Notificaciones
    var notificationsEnabled by mutableStateOf(true)
    var expandedNotification by mutableStateOf(false)
    val optionsNotifications = listOf("Nunca", "1 Día antes", "3 Días antes", "1 Semana antes", "2 Semanas antes", "3 Semanas antes", "1 Mes antes")
    var selectedTime = mutableStateOf(optionsNotifications[3])
    var textFieldSizeNotification by mutableStateOf(Size.Zero)

    fun toggleNotifications(enabled: Boolean, context: Context) {
        notificationsEnabled = enabled
        savePreferences(context)
    }

    fun selectNotificationTime(option: String, context: Context) {
        selectedTime.value = option
        savePreferences(context)
        expandedNotification = false
    }

    // preferencias
    var expandedPreferences by mutableStateOf(false)
    var textFieldSize by mutableStateOf(Size.Zero)
    val preferences = listOf("Conciertos", "Festivales de música", "Exposiciones de arte", "Funciones de teatro", "Presentaciones de danza", "Ferias del libro", "Festivales de cine")
    val selectedPreferences =  mutableStateListOf<String>()

    fun togglePreference(option: String, context: Context) {
        if (selectedPreferences.contains(option)) {
            selectedPreferences.remove(option)
        } else {
            selectedPreferences.add(option)
        }
        savePreferences(context)
    }

    // Permissions
    var isLocationEnabled by mutableStateOf(false)

    fun toggleLocationPermission(context: Context) {
        if (checkLocationPermission(context)) {
            isLocationEnabled = !isLocationEnabled
            savePreferences(context)
        }
    }

    private fun checkLocationPermission(context: Context): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    fun loadPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        isLocationEnabled = sharedPreferences.getBoolean("isLocationEnabled", false)
        notificationsEnabled = sharedPreferences.getBoolean("notificationsEnabled", true)
        selectedTime.value = sharedPreferences.getString("selectedTime", optionsNotifications[3]) ?: optionsNotifications[3]
        selectedPreferences.clear()
        selectedPreferences.addAll(sharedPreferences.getStringSet("selectedPreferences", emptySet()) ?: emptySet())
    }

    private fun savePreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isLocationEnabled", isLocationEnabled)
            putBoolean("notificationsEnabled", notificationsEnabled)
            putString("selectedTime", selectedTime.value)
            putStringSet("selectedPreferences", selectedPreferences.toSet())
            apply()
        }
    }

    // Función para manejar el resultado de la solicitud de permisos
    fun handleLocationPermissionResult(isGranted: Boolean, context: Context) {
        if (isGranted) {
            Toast.makeText(context, R.string.permits_granted, Toast.LENGTH_SHORT).show()
            isLocationEnabled = true
        } else {
            Toast.makeText(context, R.string.permits_denied, Toast.LENGTH_SHORT).show()
            isLocationEnabled = false
        }
        savePreferences(context)
    }

    // Función para alternar el estado de permiso de ubicación
    fun toggleLocationPermission(enabled: Boolean, context: Context, launchPermissionRequest: () -> Unit) {
        if (enabled) {
            if (!checkLocationPermission(context)) {
                launchPermissionRequest()
            } else {
                isLocationEnabled = true
                Toast.makeText(context,  R.string.permits_granted, Toast.LENGTH_SHORT).show()
                savePreferences(context)
            }
        } else {
            isLocationEnabled = false
            Toast.makeText(context, R.string.permits_denied, Toast.LENGTH_SHORT).show()
            savePreferences(context)
        }
    }
}
