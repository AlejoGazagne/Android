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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.SharedViewModel

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navController = rememberNavController(), sharedViewModel = SharedViewModel())
}

@Composable
fun ProfileScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    MainScaffold(navController = navController) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            //.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Profile(modifier = Modifier, navController, sharedViewModel)
        }
    }
}

@Composable
fun Profile(modifier: Modifier, navController: NavController, sharedViewModel: SharedViewModel) {
    val email: String by sharedViewModel.email.observeAsState(initial = "username@gmail.com")
    val username: String by sharedViewModel.username.observeAsState(initial = "Username")

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ProfileImage(modifier =Modifier)
        Spacer(modifier = Modifier.height(80.dp))
        // Nombre del usuario y correo
        UsernameAndEmail(modifier = modifier, sharedViewModel)
        Spacer(modifier = Modifier.height(20.dp))
        // Menú de opciones
        Menu(modifier = modifier, navController, sharedViewModel)
    }
}

@Composable
fun Menu(modifier: Modifier, navController: NavController, sharedViewModel: SharedViewModel){
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            MenuItem(
                icon = Icons.Default.Edit,
                text = "Edit Profile",
                onClick = {
                    navController.navigate(Screen.EditProfile.route)
                }
            )
            HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
            MenuItem(
                icon = Icons.Default.Settings,
                text = "Settings",
                onClick = { /* Acción del menú */ }
            )
            HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
            MenuItem(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                text = "Logout",
                textColor = Color.Red,
                iconColor = Color.Red,
                onClick = {
                    sharedViewModel.clearData()
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
    textColor: Color = Color.Black,
    iconColor: Color = Color.DarkGray,
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
                tint = Color.Gray,
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
            .background(
                Color(0xFF1F1F1F),
            )
    ) {
        Text(
            text = "Profile",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(16.dp)
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
fun UsernameAndEmail(modifier: Modifier, sharedViewModel: SharedViewModel) {
    val email: String by sharedViewModel.email.observeAsState(initial = "user@gmal.com")
    val username: String by sharedViewModel.username.observeAsState(initial = "Username")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = username,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = email,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}