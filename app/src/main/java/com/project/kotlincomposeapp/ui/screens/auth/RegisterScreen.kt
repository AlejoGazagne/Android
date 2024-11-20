package com.project.kotlincomposeapp.ui.screens.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.ui.navigation.Screen
import com.project.kotlincomposeapp.ui.viewsModels.RegisterViewModel
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    RegisterScreen(navController = rememberNavController())
}

@Composable
fun RegisterScreen(navController: NavController) {
    val viewModel: RegisterViewModel = hiltViewModel()
    DismissKeyboardOnClick {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )
        {
            Register(modifier = Modifier.fillMaxWidth(), viewModel, navController)
        }
    }
}

@Composable
fun Register(modifier: Modifier, viewModel: RegisterViewModel, navController: NavController) {
    val current = LocalContext.current
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val username: String by viewModel.username.observeAsState(initial = "")
    val registerEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val registerState by viewModel.registerState.collectAsState()

    val focusRequesterPassword = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    if(isLoading){
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LaunchedEffect(registerState) {
                when (registerState) {
                    is Resource.Success -> {
                        delay(1200)
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                        viewModel.resetRegisterState()
                    }
                    is Resource.Error -> {
                        Toast.makeText(current, R.string.user_or_password_invalid, Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Loading -> {
                        // Opcional: manejar un estado de carga adicional
                    }
                }
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

            Text(
                text = stringResource(id = R.string.register),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )

            Text(
                text = stringResource(id = R.string.welcome_message),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.Start)
            )

            FieldInput(stringResource(id = R.string.username), username, Icons.Default.Person, {
                viewModel.onRegisterChanged(email, password, it)
            }, focusRequesterPassword)

            FieldInput(stringResource(id = R.string.email), email, Icons.Default.MailOutline, {
                viewModel.onRegisterChanged(it, password, username)
            }, focusRequesterPassword)

            FieldPassword(
                password,
                { viewModel.onRegisterChanged(email, it, username) },
                focusRequesterPassword,
                focusManager,
                registerEnable
            ) {
            }

            TextLogin(modifier = Modifier.align(Alignment.Start), navController)

            Spacer(modifier = Modifier.weight(0.4F))
            ButtonRegister(registerEnable) {
                viewModel.onLoginSelected(email, password, username)
            }
            Spacer(modifier = Modifier.weight(2.6F))
        }
    }
}

@Composable
fun TextLogin(modifier: Modifier, navController: NavController) {
    Row (
        modifier = modifier
            .padding(top = 10.dp, bottom = 25.dp)
    ) {
        Text(stringResource(id = R.string.have_account))
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            stringResource(id = R.string.login_message),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
        )
    }
}

@Composable
fun ButtonRegister(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(onClick = {
        onLoginSelected()
    } ,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .size(50.dp),
        enabled = loginEnable
    ) {
        Text(text = stringResource(id = R.string.login))
    }
}

@Composable
fun FieldInput(label: String, input: String, icon: ImageVector, onTextFieldChanged: (String) -> Unit, focusRequesterPassword: FocusRequester) {
    TextField(
        value = input,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(label) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.LightGray.copy(alpha = 0.6f),
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.4f)

        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
    )
}
