package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.ui.viewsModels.SharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    BackBar(navController){ paddingValues ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                EditProfile(modifier = Modifier, navController, sharedViewModel)
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackBar(navController: NavController, content: @Composable (PaddingValues) -> Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Atras") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = content

    )
}

@Composable
fun EditProfile(modifier: Modifier, navController: NavController, sharedViewModel: SharedViewModel){

    EditProfileImage(modifier = modifier)
    EditEmail(modifier = modifier, sharedViewModel)
}

@Composable
fun EditEmail(modifier: Modifier, sharedViewModel: SharedViewModel) {

}

@Composable
fun EditProfileImage(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(120.dp),
        //.border(2.dp, Color.Gray, CircleShape),
        contentAlignment = Alignment.BottomEnd,
    ){
        Image(painter = painterResource(id = R.drawable.iua_logo),
            contentDescription = "Profile Image",
            modifier = modifier
                .size(120.dp)
                .clip(shape = CircleShape)
            //.border(2.dp, Color.Gray, CircleShape),
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Icon",
            modifier = Modifier
                .size(35.dp)
                .background(Color.Blue, CircleShape)
                .padding(4.dp)
                .clip(CircleShape),
            tint = Color.White
        )
    }
}