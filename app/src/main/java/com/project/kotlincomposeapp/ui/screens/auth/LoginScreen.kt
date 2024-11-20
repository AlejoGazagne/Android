package com.project.kotlincomposeapp.ui.screens.auth

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.domain.model.Resource
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
    val viewModel: LoginViewModel = hiltViewModel()
    DismissKeyboardOnClick {
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp)
        )
        {
            Login(modifier = Modifier.fillMaxWidth(), viewModel, navController)
        }
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel, navController: NavController) {
    val current = LocalContext.current
    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val loginState by viewModel.loginState.collectAsState()

    // Para controlar el foco entre los campos
    val focusRequesterPassword = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    if(isLoading){
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            LaunchedEffect(loginState) {
                when (loginState) {
                    is Resource.Success -> {
                        delay(1200)
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                        viewModel.resetLoginState()
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
                text = stringResource(id = R.string.login),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )

            Text(
                text = stringResource(id = R.string.sing_in),
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .align(Alignment.Start)
            )

            FieldEmail(email, { viewModel.onLoginChanged(it, password) }, focusRequesterPassword)

            FieldPassword(
                password,
                { viewModel.onLoginChanged(email, it) },
                focusRequesterPassword,
                focusManager,
                loginEnable
            ) {
            }

            TextRegister(modifier = Modifier.align(Alignment.Start), navController)

            Spacer(modifier = Modifier.weight(0.4F))
            ButtonLogin(loginEnable) {
                viewModel.onLoginSelected(email, password)
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
fun FieldEmail(email: String, onTextFieldChanged: (String) -> Unit, focusRequesterPassword: FocusRequester) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(stringResource(id = R.string.email)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Email Icon"
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
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusRequesterPassword.requestFocus()
            }
        )
    )
}

@Composable
fun FieldPassword(
    password: String,
    onTextFieldChanged: (String) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    loginEnable: Boolean,
    onLogin: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        label = { Text(stringResource(id = R.string.password)) },
        singleLine = true,
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
            .padding(vertical = 8.dp)
            .focusRequester(focusRequester),
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.LightGray.copy(alpha = 0.6f),
            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.4f)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (loginEnable) {
                    onLogin()
                } else {
                    focusManager.clearFocus()
                }
            }
        )
    )
}

@Composable
fun TextRegister(modifier: Modifier, navController: NavController) {
    Row (
        modifier = modifier
            .padding(top = 10.dp, bottom = 25.dp)
    ) {
        Text(stringResource(id = R.string.dont_have_account))
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            stringResource(id = R.string.register),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable {
                    navController.navigate(Screen.Register.route){
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
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
        Text(text = stringResource(id = R.string.login))
    }
}