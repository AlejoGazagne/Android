package com.project.kotlincomposeapp.ui.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.ui.components.BackBar
import com.project.kotlincomposeapp.ui.components.Spacer
import android.Manifest
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.ui.viewsModels.SettingsViewModel

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    SettingsScreen(navController = rememberNavController())
}

@Composable
fun SettingsScreen(navController: NavController) {
    val viewModel: SettingsViewModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadPreferences(context)
    }

    BackBar(navController){ paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ){
            Settings(modifier = Modifier, navController, viewModel, context)
        }
    }
}

@Composable
fun Settings(modifier: Modifier, navController: NavController, viewModel: SettingsViewModel, context: Context) {
    Text(text = stringResource(id = R.string.settings), fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.padding(10.dp))
    NotificationTimeSelector(viewModel, context)
    Spacer(modifier = Modifier.padding(16.dp))
    PreferencesSelectDropdown(viewModel, context)
    Spacer(modifier = Modifier.padding(16.dp))
    LocationPermissionSwitch(viewModel, context)

}

@Composable
fun NotificationTimeSelector(viewModel: SettingsViewModel, context: Context) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.enable_notifications), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Switch(
            checked = viewModel.notificationsEnabled,
            onCheckedChange = {
                viewModel.toggleNotifications(it)
                viewModel.savePreferences(context)
            }
        )
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.notify_me),
            fontSize = 18.sp,
            style = MaterialTheme.typography.labelLarge
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    viewModel.textFieldSizeNotification = coordinates.size.toSize()
                }
                .clickable(
                    enabled = viewModel.notificationsEnabled, // Disable interaction if notifications are disabled
                    onClick = {
                        if (viewModel.notificationsEnabled) viewModel.expandedNotification = true
                    }
                )
                .padding(8.dp)
        ) {
            Text(
                text = viewModel.selectedTime.value,
                fontSize = 16.sp,
                color = if (viewModel.notificationsEnabled) LocalContentColor.current else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) // Adjust text color when disabled
            )
        }
        DropdownMenu(
            expanded = viewModel.expandedNotification,
            onDismissRequest = { viewModel.expandedNotification = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { viewModel.textFieldSizeNotification.width.toDp() })
        ) {
            viewModel.optionsNotifications.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        viewModel.selectedTime.value = option
                        viewModel.savePreferences(context)
                        viewModel.expandedNotification = false
                    }
                )
            }
        }
    }
}

@Composable
fun PreferencesSelectDropdown(viewModel: SettingsViewModel, context: Context) {
    Text(text = stringResource(id = R.string.preferences), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
    Spacer(modifier = Modifier.padding(5.dp))

    Column(modifier = Modifier.fillMaxWidth()) {
        // Title for the dropdown
        Text(
            text = stringResource(id = R.string.search_preferences),
            fontSize = 18.sp,
            style = MaterialTheme.typography.labelLarge
        )

        // Box to display selected options and trigger dropdown
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // Capture the size of the box
                    viewModel.textFieldSize = coordinates.size.toSize()
                }
                .clickable { viewModel.expandedPreferences = true }
                .padding(8.dp)
        ) {
            Text(
                text = if (viewModel.selectedPreferences.isEmpty()) stringResource(id = R.string.select_options) else viewModel.selectedPreferences.joinToString(", "),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // DropdownMenu to show options
        DropdownMenu(
            expanded = viewModel.expandedPreferences,
            onDismissRequest = { viewModel.expandedPreferences = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { viewModel.textFieldSize.width.toDp() })
        ) {
            viewModel.preferences.forEach { option ->
                val isSelected = viewModel.selectedPreferences.contains(option)

                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(option)
                            // Show a checkmark if the option is selected
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected"
                                )
                            }
                        }
                    },
                    onClick = {
                        if (isSelected) {
                            viewModel.selectedPreferences.remove(option) // Remove if already selected
                            viewModel.savePreferences(context)
                        } else {
                            viewModel.selectedPreferences.add(option) // Add if not selected
                            viewModel.savePreferences(context)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun LocationPermissionSwitch( viewModel: SettingsViewModel, context: Context) {
    // Launcher to request the permission
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, R.string.permits_granted, Toast.LENGTH_SHORT).show()
            viewModel.isLocationEnabled = true
            viewModel.savePreferences(context)
        } else {
            Toast.makeText(context, R.string.permits_denied, Toast.LENGTH_SHORT).show()
            viewModel.isLocationEnabled = false
            viewModel.savePreferences(context)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.acces_ubication), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Switch(
            checked = viewModel.isLocationEnabled,
            onCheckedChange = { enabled ->
                if (enabled) {
                    if (!viewModel.checkLocationPermission(context)) {
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    } else {
                        viewModel.isLocationEnabled = true
                        Toast.makeText(context, "Permiso de ubicación ya concedido", Toast.LENGTH_SHORT).show()
                        viewModel.savePreferences(context)
                    }
                } else {
                    // If the switch is turned off, disable location access
                    viewModel.isLocationEnabled = false
                    Toast.makeText(context, "Acceso a la ubicación deshabilitado", Toast.LENGTH_SHORT).show()
                    viewModel.savePreferences(context)
                }
            }
        )
    }
}