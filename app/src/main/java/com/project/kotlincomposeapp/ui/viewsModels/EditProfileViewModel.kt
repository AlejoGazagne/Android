package com.project.kotlincomposeapp.ui.viewsModels

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.model.UserModel
import com.project.kotlincomposeapp.domain.usecase.user.DeleteAllUsersUseCase
import com.project.kotlincomposeapp.domain.usecase.user.GetUserUseCase
import com.project.kotlincomposeapp.domain.usecase.user.SaveUserUseCase
import com.project.kotlincomposeapp.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val deleteUserUseCase: DeleteAllUsersUseCase
): ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private var _user = MutableStateFlow<Resource<UserModel>>(Resource.Loading())
    var user: StateFlow<Resource<UserModel>> = _user

    init {
        viewModelScope.launch {
            getUserUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _username.value = resource.data?.name.orEmpty()
                        _email.value = resource.data?.email.orEmpty()
                        _password.value = resource.data?.password.orEmpty()
                    }

                    is Resource.Error -> {
                        Log.e("EditProfileViewModel", "Error: ${resource.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("EditProfileViewModel", "Loading")
                    }
                }
            }
        }
    }



    private var _saveChanges = MutableLiveData<Boolean>()
    var saveChanges: LiveData<Boolean> = _saveChanges

    private fun isValidUsername(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidName(name: String): Boolean = name.length > 4

    fun onSaveChanges(email: String, password: String, name: String) {
        _email.value = email
        _password.value = password
        _username.value = name
        _saveChanges.value = isValidUsername(email) && isValidPassword(password) && isValidName(name)
    }

    fun saveChanges(email: String, password: String, name: String, navController: NavController) {
        viewModelScope.launch {
            saveUserUseCase(UserModel(name, email, password)).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _username.value = resource.data?.name.orEmpty()
                        _email.value = resource.data?.email.orEmpty()
                        _password.value = resource.data?.password.orEmpty()
                        _user.value = resource
                        Log.d("EditProfileViewModel", "Success?: ${resource.data}")
                        Log.d("EditProfileViewModel", "Success: ${_username.value}, ${_email.value}, ${_password.value}")
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(Screen.Profile.route) { inclusive = true }
                        }
                    }

                    is Resource.Error -> {
                        Log.e("EditProfileViewModel", "Error: ${resource.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("EditProfileViewModel", "Loading")
                    }
                }
            }
        }
    }

    fun logout(navController: NavController){
        viewModelScope.launch {
            deleteUserUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d("EditProfileViewModel", "Success")
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }

                    is Resource.Error -> {
                        Log.e("EditProfileViewModel", "Error: ${resource.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("EditProfileViewModel", "Loading")
                    }
                }
            }
        }
    }

    fun changeSaveUser(){
        _saveChanges.value = !_saveChanges.value!!
    }
}