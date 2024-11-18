package com.project.kotlincomposeapp.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import android.Manifest
import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.HorizontalDivider
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
    LaunchedEffect(viewModel.languageChanged) {
        // Forzar recomposición
    }

    BackBar(
        modifier = Modifier, navController,
        title = stringResource(id = R.string.settings),
    ){ paddingValues ->
        Settings(modifier = Modifier, navController, viewModel, context, paddingValues)
    }
}

@Composable
fun Settings(
    modifier: Modifier,
    navController: NavController,
    viewModel: SettingsViewModel,
    context: Context,
    paddingValues: PaddingValues
) {
    Column (
        modifier = modifier
            .padding(paddingValues)
    ){
        HorizontalDivider(modifier = modifier.fillMaxWidth().padding(bottom = 12.dp))
        NotificationTimeSelector(viewModel, context, modifier)
        HorizontalDivider(modifier = modifier.fillMaxWidth().padding(bottom = 12.dp))
        PreferencesSelectDropdown(viewModel, context, modifier)
        HorizontalDivider(modifier = modifier.fillMaxWidth().padding(bottom = 12.dp))
        LocationPermissionSwitch(viewModel, context, modifier)
        HorizontalDivider(modifier = modifier.fillMaxWidth().padding(bottom = 12.dp))
        LanguageSelector(viewModel, context, modifier)
    }
}

@Composable
fun NotificationTimeSelector(viewModel: SettingsViewModel, context: Context, modifier: Modifier) {
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewModel.handleNotificationPermissionResult(isGranted, context)
    }

    Column (modifier = modifier.padding(horizontal = 16.dp)) {
        Text(text = stringResource(id = R.string.enable_notifications),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f)
        )

        Row(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.width(200.dp)) {
                Text(
                    text = stringResource(id = R.string.notify_me),
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(text = stringResource(id = R.string.notification_message),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .onGloballyPositioned { coordinates ->
                            viewModel.textFieldSizeNotification = coordinates.size.toSize()
                        }
                        .clickable(
                            enabled = viewModel.notificationsEnabled, // Disable interaction if notifications are disabled
                            onClick = {
                                if (viewModel.notificationsEnabled) viewModel.expandedNotification = true
                            }
                        )
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = viewModel.selectedTime.value,
                        fontSize = 14.sp,
                        color = if (viewModel.notificationsEnabled)
                            LocalContentColor.current
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) // Adjust text color when disabled
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
                            onClick = { viewModel.selectNotificationTime(option, context) }
                        )
                    }
                }
            }
            Switch(
                checked = viewModel.notificationsEnabled,
                onCheckedChange = {
                    viewModel.toggleNotifications(it, context){
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            )
        }
    }

}

@Composable
fun PreferencesSelectDropdown(viewModel: SettingsViewModel, context: Context, modifier: Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(text = stringResource(id = R.string.preferences),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f)
        )

        Column(modifier = modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            // Title for the dropdown
            Text(
                text = stringResource(id = R.string.search_preferences),
                fontSize = 16.sp,
                style = MaterialTheme.typography.labelLarge
            )
            Text(text = stringResource(id = R.string.preference_message),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // Capture the size of the box
                        viewModel.textFieldSize = coordinates.size.toSize()
                    }
                    .clickable { viewModel.expandedPreferences = true }
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = if (viewModel.selectedPreferences.isEmpty()) stringResource(id = R.string.select_options) else viewModel.selectedPreferences.joinToString(", "),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

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
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected"
                                    )
                                }
                            }
                        },
                        onClick = { viewModel.togglePreference(option, context) },
                    )
                }
            }
        }
    }
}

@Composable
fun LocationPermissionSwitch(viewModel: SettingsViewModel, context: Context, modifier: Modifier) {
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewModel.handleLocationPermissionResult(isGranted, context)
    }

    Column (modifier = modifier.padding(horizontal = 16.dp)) {
        Text(text = stringResource(id = R.string.permissions),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = modifier.width(250.dp).padding(vertical = 12.dp)) {
                Text(
                    text = stringResource(id = R.string.acces_ubication),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text( text = stringResource(id = R.string.ubication_message),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f)
                )
            }
            Switch(
                checked = viewModel.isLocationEnabled,
                onCheckedChange = { enabled ->
                    viewModel.toggleLocationPermission(
                        enabled = enabled,
                        context = context,
                        launchPermissionRequest = { locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
                    )
                }
            )
        }
    }
}

@Composable
fun LanguageSelector(viewModel: SettingsViewModel, context: Context, modifier: Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(id = R.string.language),
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.80f)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.select_language),
                fontSize = 16.sp,
                style = MaterialTheme.typography.labelLarge
            )

            DropdownMenu(
                expanded = viewModel.expandedLanguageMenu,
                onDismissRequest = { viewModel.expandedLanguageMenu = false },
                modifier = Modifier.width(200.dp)
            ) {
                viewModel.languages.forEach { language ->
                    DropdownMenuItem(
                        text = { Text(language.displayName) },
                        onClick = {
                            viewModel.setLanguage(language, context)
                            viewModel.expandedLanguageMenu = false
                        }
                    )
                }
            }

            Box(modifier = Modifier.clickable { viewModel.expandedLanguageMenu = true }) {
                Text(
                    text = viewModel.selectedLanguage.displayName,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}