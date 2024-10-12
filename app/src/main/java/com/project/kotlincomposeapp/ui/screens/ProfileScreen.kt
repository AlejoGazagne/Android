package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.navigation.Screen

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navController = rememberNavController())
}

@Composable
fun ProfileScreen(navController: NavController) {
    MainScaffold(navController = navController) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            //.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Profile(modifier = Modifier, navController)
        }
    }
}

@Composable
fun Profile(modifier: Modifier, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ProfileImage(modifier = Modifier)
        Spacer(modifier = Modifier.height(80.dp))
        // Nombre del usuario y correo
        UsernameAndEmail(modifier = modifier)
        Spacer(modifier = Modifier.height(20.dp))
        // Menú de opciones
        Menu(modifier = modifier, navController)
    }
}

@Composable
fun Menu(modifier: Modifier, navController: NavController){
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column (
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
        ) {
            MenuItem(
                icon = Icons.Default.Edit,
                text = stringResource(id = R.string.edit_profile),
                onClick = {
                    navController.navigate(Screen.EditProfile.route)
                }
            )
            HorizontalDivider(thickness = 0.5.dp)
            MenuItem(
                icon = Icons.Default.Settings,
                text = stringResource(id = R.string.settings),
                onClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
            HorizontalDivider(thickness = 0.5.dp)
            MenuItem(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                text = stringResource(id = R.string.logout),
                textColor = Color.Red,
                iconColor = Color.Red,
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                showArrow = false
            )
        }
    }
}

@Composable
fun MenuItem(
    icon: ImageVector,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    iconColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit,
    showArrow: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (showArrow){
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun ProfileImage(modifier: Modifier) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        Text(
            text = stringResource(id = R.string.profile),
            color = MaterialTheme.colorScheme.background,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(16.dp)
        )
        // Imagen del usuario
        Image(
            painter = painterResource(id = R.drawable.perfil),
            contentDescription = "User Image",
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 70.dp)
                .clip(CircleShape)
                .background(Color.White, CircleShape),
            contentScale = ContentScale.Crop
        )

    }
}

@Composable
fun UsernameAndEmail(modifier: Modifier) {
    val email: String by remember { mutableStateOf("user@gmail.com") }
    val username: String by remember { mutableStateOf("Username") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = username,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = email,
            fontSize = 14.sp,
        )
    }
}