package com.project.kotlincomposeapp.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.project.kotlincomposeapp.R
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.ui.components.BackBar
import com.project.kotlincomposeapp.ui.viewsModels.EditProfileViewModel

@Preview(showBackground = true)
@Composable
fun PreviewEditProfileScreen() {
    EditProfileScreen(navController = rememberNavController())
}

@Composable
fun EditProfileScreen(navController: NavController) {
    val viewModel: EditProfileViewModel = hiltViewModel()
    BackBar(modifier = Modifier,
        navController,
        title = stringResource(id = R.string.edit_profile),
        "profile"
    ){ paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ){
            EditProfile(modifier = Modifier, navController, viewModel)
        }
    }
}

@Composable
fun EditProfile(modifier: Modifier, navController: NavController, viewModel: EditProfileViewModel){
    val context = LocalContext.current
    val email by viewModel.email.collectAsState()
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val buttonSaveChange: Boolean by viewModel.saveChanges.observeAsState(initial = false)

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
        EditParameter(modifier = modifier, email, stringResource(id = R.string.email), Icons.Default.Email) {
            viewModel.onSaveChanges(
                it,
                password,
                username
            )
        }

        EditParameter(modifier = modifier, username, stringResource(id = R.string.username), Icons.Default.Person) {
            viewModel.onSaveChanges(
                email,
                password,
                it
            )
        }

        EditParameter(modifier = modifier, password, stringResource(id = R.string.password), Icons.Default.VisibilityOff) {
            viewModel.onSaveChanges(
                email,
                it,
                username
            )
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
                    onClick = { viewModel.sendNotification(context) },
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
                    onClick = {
                        Log.e("EditProfileScreen", "Save Changes: $email, $password, $username")
                        viewModel.saveChanges(email, password, username, navController) },
                    enabled = buttonSaveChange,
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
                        contentColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
                        disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
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
    onTextFieldChanged: (String) -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val passwordLabel = stringResource(id = R.string.password)

    TextField(
        value = input,
        onValueChange = onTextFieldChanged,
        enabled = label != stringResource(id = R.string.email),
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
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledTextColor = Color.Gray, // Color del texto cuando está deshabilitado
            disabledContainerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f), // Color de fondo cuando está deshabilitado
            disabledLabelColor = Color.DarkGray, // Color de la etiqueta cuando está deshabilitado
            disabledTrailingIconColor = Color.Gray // Color del icono cuando está deshabilitado
        ),
    )
}

@Composable
fun EditProfileImage(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(150.dp)
        ,
        contentAlignment = Alignment.BottomEnd,
    ){
        Image(painter = painterResource(id = R.drawable.perfil),
            contentDescription = "Profile Image",
            modifier = modifier
                .size(150.dp)
                .clip(shape = CircleShape)
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