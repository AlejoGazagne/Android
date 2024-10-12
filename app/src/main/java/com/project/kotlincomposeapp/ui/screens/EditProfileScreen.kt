package com.project.kotlincomposeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.ui.components.BackBar

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen(navController = rememberNavController())
}

@Composable
fun EditProfileScreen(navController: NavController) {
    BackBar(navController){ paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ){
            EditProfile(modifier = Modifier, navController)
        }
    }
}

@Composable
fun EditProfile(modifier: Modifier, navController: NavController){
    var emailState by remember { mutableStateOf("user@gmail.com") }
    var usernameState by remember { mutableStateOf("username") }
    var passwordState by remember { mutableStateOf("pass123") }

    Text(text = stringResource(id = R.string.edit_profile), fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        EditProfileImage(modifier = modifier)
    }
    Column(
        modifier = Modifier
    ) {
        EditParameter(modifier = modifier, emailState, stringResource(id = R.string.email), Icons.Default.Email) { newEmail ->
            emailState = newEmail
        }
        EditParameter(modifier = modifier, usernameState, stringResource(id = R.string.username), Icons.Default.Person) { newUsername ->
            usernameState = newUsername
        }
        EditParameter(modifier = modifier, passwordState, stringResource(id = R.string.password), Icons.Default.VisibilityOff) { newPassword ->
            passwordState = newPassword
        }

        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /* Acción del primer botón */ },
                    modifier = Modifier
                        .width(130.dp)
                        .height(47.dp)
                        .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = stringResource(id = R.string.cancel))
                }
                Button(
                    onClick = { /* Acción del primer botón */ },
                    modifier = Modifier
                        .width(130.dp)
                        .height(47.dp)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(8.dp)
                        ),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = stringResource(id = R.string.save), color = MaterialTheme.colorScheme.background)
                }
            }
        }
    }
}

@Composable
fun EditParameter(
    modifier: Modifier,
    input: String,
    label: String,
    icon: ImageVector,
    onInputChange: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val passwordLabel = stringResource(id = R.string.password)

    TextField(
        value = input,
        onValueChange = onInputChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .padding(vertical = 10.dp)
            .clip(RoundedCornerShape(15.dp)),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (label == passwordLabel && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon  = {
            if (label == passwordLabel) {
                val image = if (passwordVisible) {
                    Icons.Default.Visibility
                } else {
                    Icons.Default.VisibilityOff
                }

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle Password Visibility")
                }
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon"
                )
            }
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface, // Color de fondo cuando está enfocado
            unfocusedContainerColor = MaterialTheme.colorScheme.background, // Color de fondo cuando no está enfocado
        ),
    )
}

/*@Composable
fun EditEmail(modifier: Modifier, email: String, onEmailChange: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Email", modifier = Modifier.padding(0.dp)) },
        singleLine = true,
        modifier = Modifier
            .padding(7.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp)),
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
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            focusedContainerColor = MaterialTheme.colorScheme.surface, // Color de fondo cuando está enfocado
            unfocusedContainerColor = MaterialTheme.colorScheme.background, // Color de fondo cuando no está enfocado
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
}*/

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
                .border(5.dp, MaterialTheme.colorScheme.background, CircleShape)
                .size(40.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .padding(8.dp)
                .clip(CircleShape),
            tint = MaterialTheme.colorScheme.background
        )
    }
}