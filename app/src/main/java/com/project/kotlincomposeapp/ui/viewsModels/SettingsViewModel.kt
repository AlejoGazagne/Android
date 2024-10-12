package com.project.kotlincomposeapp.ui.viewsModels

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    // Notificaciones
    var notificationsEnabled by mutableStateOf(true)
    var expandedNotification by mutableStateOf(false)
    val optionsNotifications = listOf("Nunca", "1 Día antes", "3 Días antes", "1 Semana antes", "2 Semanas antes", "3 Semanas antes", "1 Mes antes")
    var selectedTime = mutableStateOf(optionsNotifications[3])
    var textFieldSizeNotification by mutableStateOf(Size.Zero)

    fun toggleNotifications(enabled: Boolean) {
        notificationsEnabled = enabled
    }

    // preferencias
    var expandedPreferences by mutableStateOf(false)
    var textFieldSize by mutableStateOf(Size.Zero)
    val preferences = listOf("Conciertos", "Festivales de música", "Exposiciones de arte", "Funciones de teatro", "Presentaciones de danza", "Ferias del libro", "Festivales de cine")
    val selectedPreferences =  mutableStateListOf<String>()

    // Permissions
    var isLocationEnabled by mutableStateOf(false)

    fun checkLocationPermission(context: Context): Boolean {
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

    fun savePreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isLocationEnabled", isLocationEnabled)
            putBoolean("notificationsEnabled", notificationsEnabled)
            putString("selectedTime", selectedTime.value)
            putStringSet("selectedPreferences", selectedPreferences.toSet())
            apply()
        }
    }

    fun requestLocationPermission(context: Context, onPermissionResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isGranted = checkLocationPermission(context)
            if (isGranted) {
                Toast.makeText(context, "Permiso de ubicación ya concedido", Toast.LENGTH_SHORT).show()
                isLocationEnabled = true
            } else {
                onPermissionResult(false)
            }
        }
    }
}