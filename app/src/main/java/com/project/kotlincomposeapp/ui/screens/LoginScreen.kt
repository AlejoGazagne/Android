package com.project.kotlincomposeapp.ui.screens

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.LoginViewModel
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel = LoginViewModel()
    DismissKeyboardOnClick {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 15.dp)
        )
        {
            Login(modifier = Modifier.fillMaxWidth(), viewModel, navController)
        }
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navController: NavController) {
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)

    if(isLoading){
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LaunchedEffect(Unit) {
                delay(1500)
                navController.navigate(Screen.Home.route){
                    popUpTo(Screen.Login.route){
                        inclusive = true
                    }
                }
                viewModel.resetLoading()
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1.3F))
            LoginImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.weight(0.4F))
            // Título "Login"
            Text(
                text = "Login",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )

            // Subtítulo "Please Sign in to continue."
            Text(
                text = "Please Sign in to continue.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.Start)
            )

            FieldEmail(email) { viewModel.onLoginChanged(it, password) }

            FieldPassword(password) { viewModel.onLoginChanged(email, it) }

            TextRegister(modifier = Modifier.align(Alignment.Start))

            Spacer(modifier = Modifier.weight(0.4F))
            ButtonLogin(loginEnable) {
                viewModel.onLoginSelected()
            }
            Spacer(modifier = Modifier.weight(2.6F))
        }
    }
}

fun hideKeyboard(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(null, 0)
}

@Composable
fun DismissKeyboardOnClick(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                keyboardController?.hide()
                hideKeyboard(context)
            }
    ) {
        content()
    }
}

@Composable
fun LoginImage(modifier: Modifier) {
    Image(painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo",
        modifier = modifier
            .size(200.dp)
    )
}

@Composable
fun FieldEmail(email: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text("Email") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Email Icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F7FA), shape = RoundedCornerShape(20.dp))
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun FieldPassword(password: String, onTextFieldChanged: (String) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }
    TextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text("Password") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Password Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F7FA), shape = RoundedCornerShape(20.dp))
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun TextRegister(modifier: Modifier) {
    Row (
        modifier = modifier
            .padding(top = 10.dp, bottom = 25.dp)
    ) {
        Text("Don't have an account?")
        Spacer(modifier = Modifier.width(7.dp))
        Text("Register",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable {
                    // TODO: Hacer registro
                }
        )
    }

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