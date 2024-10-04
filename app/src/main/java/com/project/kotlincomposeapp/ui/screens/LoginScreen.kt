package com.project.kotlincomposeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.LoginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    Box(modifier = Modifier
        .background(Color.White)
        .padding(horizontal = 15.dp))
    {
        Login(modifier = Modifier.fillMaxWidth(), viewModel, navController)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navController: NavController) {
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    if(isLoading){
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LaunchedEffect(Unit) {
                delay(3000)
                navController.navigate(Screen.Home.route)
                viewModel.resetLoading()
            }
        }
    } else {
        Column(modifier = modifier) {
            Spacer(modifier = Modifier.weight(1.3F))
            LoginImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.weight(0.4F))
            FieldEmail(email) { viewModel.onLoginChanged(it, password) }
            FieldPassword(password) { viewModel.onLoginChanged(email, it) }
            TextRegister(modifier = Modifier.align(Alignment.Start))
            ButtonLogin(loginEnable) {
                viewModel.onLoginSelected()
            }
            Spacer(modifier = Modifier.weight(2.6F))
        }
    }
}

@Composable
fun LoginImage(modifier: Modifier) {
    Image(painter = painterResource(id = R.drawable.iua_logo),
        contentDescription = "Logo",
        modifier = modifier
            .size(200.dp)
    )
}

@Composable
fun FieldEmail(email: String, onTextFieldChanged: (String) -> Unit) {
    TextField(value = email, onValueChange = { onTextFieldChanged(it) }, label = { Text("Email") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun FieldPassword(password: String, onTextFieldChanged: (String) -> Unit) {
    TextField(value = password, onValueChange = { onTextFieldChanged(it) }, label = { Text("Password") },
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false
        ),
        singleLine = true
    )
}

@Composable
fun TextRegister(modifier: Modifier) {
    Text(
        text = "Register",
        modifier = modifier
            .padding(top = 10.dp, bottom = 25.dp)
            .clickable {
                /* TODO */
            },
    )
}

@Composable
fun ButtonLogin(loginEnable: Boolean, onLoginSelected: () -> Unit){
    Button(onClick = {
        onLoginSelected()
    } ,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .size(50.dp),
        enabled = loginEnable
    ) {
        Text(text = "Login")
    }
}