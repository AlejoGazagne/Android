package com.project.kotlincomposeapp.ui.viewsModels

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    // Notificaciones
    var notificationsEnabled by mutableStateOf(false)
    var expandedNotification by mutableStateOf(false)
    val optionsNotifications = listOf("Nunca", "1 Día antes", "3 Días antes", "1 Semana antes", "2 Semanas antes", "3 Semanas antes", "1 Mes antes")
    var selectedTime = mutableStateOf(optionsNotifications[3])
    var textFieldSizeNotification by mutableStateOf(Size.Zero)

    private fun checkNotificationPermission(context: Context): Boolean {
        val notificationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        )
        return notificationPermission == PackageManager.PERMISSION_GRANTED
    }

    fun handleNotificationPermissionResult(isGranted: Boolean, context: Context) {
        if (isGranted) {
            Toast.makeText(context, R.string.notifications_granted, Toast.LENGTH_SHORT).show()
            notificationsEnabled = true
        } else {
            Toast.makeText(context, R.string.notifications_denied, Toast.LENGTH_SHORT).show()
            notificationsEnabled = false
        }
        savePreferences(context)
    }

    fun toggleNotifications(enabled: Boolean, context: Context, launchPermissionRequest: () -> Unit) {
        if (enabled) {
            if (!checkNotificationPermission(context)) {
                launchPermissionRequest()
            } else {
                notificationsEnabled = true
                Toast.makeText(context, R.string.permits_granted, Toast.LENGTH_SHORT).show()
                savePreferences(context)
            }
        } else {
            notificationsEnabled = true
            Toast.makeText(context, R.string.revoke_notifications_manually, Toast.LENGTH_SHORT).show()
            savePreferences(context)
        }
    }

    /*fun toggleNotifications(enabled: Boolean, context: Context) {
        notificationsEnabled = enabled
        savePreferences(context)
    }*/

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

    private fun checkLocationPermission(context: Context): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        )
        return fineLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    fun loadPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        isLocationEnabled = checkLocationPermission(context)
        notificationsEnabled = checkNotificationPermission(context)
        selectedTime.value = sharedPreferences.getString("selectedTime", optionsNotifications[3]) ?: optionsNotifications[3]
        selectedPreferences.clear()
        selectedPreferences.addAll(sharedPreferences.getStringSet("selectedPreferences", emptySet()) ?: emptySet())
        loadLanguagePreference(context)
        checkLocationPermission(context)
    }

    private fun savePreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("notificationsEnabled", checkNotificationPermission(context))
            putBoolean("isLocationEnabled", checkLocationPermission(context))
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
            isLocationEnabled = true
            Toast.makeText(context, R.string.revoke_permits_manually, Toast.LENGTH_SHORT).show()
            savePreferences(context)
        }
    }

    // LEENGUAJE
    var languageChanged by mutableStateOf(false)
    var expandedLanguageMenu by mutableStateOf(false)
    val languages = listOf(Locale("en"), Locale("es"))
    var selectedLanguage: Locale by mutableStateOf(Locale.getDefault())

    fun setLanguage(language: Locale, context: Context) {
        selectedLanguage = language
        updateAppLocale(context, language)
        saveLanguagePreference(context, language)
        languageChanged = !languageChanged
    }

    private fun updateAppLocale(context: Context, language: Locale) {
        val config = Configuration(context.resources.configuration)
        config.setLocale(language)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

    private fun saveLanguagePreference(context: Context, language: Locale) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("selectedLanguage", language.language)
            apply()
        }
    }

    private fun loadLanguagePreference(context: Context) {
        val sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("selectedLanguage", Locale.getDefault().language)
        val locale = languages.find { it.language == language } ?: Locale.getDefault()
        setLanguage(locale, context)
    }
}
