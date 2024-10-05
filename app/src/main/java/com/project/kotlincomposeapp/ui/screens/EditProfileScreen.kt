package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.ui.viewsModels.SharedViewModel

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen(navController = rememberNavController(), sharedViewModel = SharedViewModel())
}

@Composable
fun EditProfileScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    BackBar(navController){ paddingValues ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
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
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    },
                        modifier = Modifier
                            //.padding(16.dp)
                            .width(50.dp)
                            //.border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
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
    var emailState by remember { mutableStateOf(sharedViewModel.email.value ?: "user@gmail.com") }
    var usernameState by remember { mutableStateOf(sharedViewModel.username.value ?: "username") }
    var passwordState by remember { mutableStateOf(sharedViewModel.password.value ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Edit Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center,
        ) {
            EditProfileImage(modifier = modifier)
        }
        Column(
            modifier = Modifier
        ) {
            EditEmail(modifier = modifier, emailState) { newEmail ->
                emailState = newEmail
            }
            EditUsername(modifier = modifier, usernameState) { newUsername ->
                usernameState = newUsername
            }
            EditPassword(modifier = modifier, passwordState) { newPassword ->
                passwordState = newPassword
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEmail(modifier: Modifier, email: String, onEmailChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email", modifier = Modifier.padding(0.dp)) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        trailingIcon  = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon"
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUsername(modifier: Modifier, email: String, onEmailChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Username", modifier = Modifier.padding(0.dp)) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        trailingIcon  = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Email Icon"
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        ),

        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPassword(modifier: Modifier, email: String, onEmailChange: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Username", modifier = Modifier.padding(0.dp)) },
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        trailingIcon  = {
            val image = if (passwordVisible) {
                Icons.Default.Visibility
            } else {
                Icons.Default.VisibilityOff
            }

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
            }
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        ),

        )
}

@Composable
fun EditProfileImage(modifier: Modifier) {
    Box(
        modifier = modifier
            //.shadow(4.dp)
            .size(150.dp)
        ,
        //.border(2.dp, Color.Gray, CircleShape),
        contentAlignment = Alignment.BottomEnd,
    ){
        Image(painter = painterResource(id = R.drawable.perfil),
            contentDescription = "Profile Image",
            modifier = modifier
                .size(150.dp)
                .clip(shape = CircleShape)
            //.border(2.dp, Color.Gray, CircleShape),
        )
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Icon",
            modifier = Modifier
                .border(5.dp, Color.White, CircleShape)
                .size(40.dp)
                .background(Color.Blue, CircleShape)
                .padding(8.dp)
                .clip(CircleShape),
            tint = Color.White
        )
    }
}