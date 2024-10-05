package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.ui.components.MainScaffold
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.LoginViewModel
import com.project.kotlincomposeapp.ui.viewsModels.SharedViewModel

/*@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}*/

@Composable
fun ProfileScreen(navController: NavController, sharedViewModel: SharedViewModel) {

    MainScaffold(navController = navController) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Profile(modifier = Modifier, navController, sharedViewModel)
        }
    }
}

@Composable
fun Profile(modifier: Modifier, navController: NavController, sharedViewModel: SharedViewModel) {
    val email: String by sharedViewModel.email.observeAsState(initial = "")

    ProfileImage(modifier = modifier)
    Username(modifier = modifier)
    Spacer(modifier = Modifier.height(30.dp))
    Mail(modifier = modifier, email)
    Spacer(modifier = Modifier.height(30.dp))
    Box(
        modifier = Modifier
            .padding(20.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    ){
        Menu(modifier = modifier, navController, sharedViewModel)
    }
}

@Composable
fun Menu(modifier: Modifier, navController: NavController, sharedViewModel: SharedViewModel){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xffebebeb), RoundedCornerShape(8.dp))
    ) {
        MenuItem(
            icon = Icons.Default.Edit,
            text = "Edit Profile",
            onClick = {
                navController.navigate(Screen.EditProfile.route)
            }
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        MenuItem(
            icon = Icons.Default.Settings,
            text = "Settings",
            onClick = { /* Acción del menú */ }
        )
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
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

@Composable
fun MenuItem(
    icon: ImageVector,
    text: String,
    textColor: Color = Color.Black,
    iconColor: Color = Color.Blue,
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

    Image(painter = painterResource(id = R.drawable.iua_logo),
        contentDescription = "Profile Image",
        modifier = modifier
            .size(120.dp)
            .clip(shape = CircleShape)
        //.border(2.dp, Color.Gray, CircleShape),
    )

}

@Composable
fun Username(modifier: Modifier) {
    Text(
        text = "Username",
        modifier = modifier
            .padding(top = 10.dp),
        color = Color.Black,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun Mail(modifier: Modifier, email: String){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFD6E4FF))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = email,
            color = Color.Black
        )
    }
}