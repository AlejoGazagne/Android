package com.project.kotlincomposeapp.ui.viewsModels

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.model.UserModel
import com.project.kotlincomposeapp.domain.usecase.user.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
): ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _registerState: MutableStateFlow<Resource<Unit>> = MutableStateFlow(Resource.Loading())
    val registerState: StateFlow<Resource<Unit>> get() = _registerState

    fun resetRegisterState() {
        _registerState.value = Resource.Loading()
    }

    fun onRegisterChanged(email: String, password: String, username: String) {
        _email.value = email
        _password.value = password
        _username.value = username
        _loginEnable.value = isValidEmail(email) && isValidPassword(password) && isValidUsername(username)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidUsername(username: String): Boolean = username.length > 4

    fun onLoginSelected(email: String, password: String, username: String){
        _isLoading.value = true
        viewModelScope.launch {
            registerUserUseCase(UserModel(username, email, password)).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _registerState.value = Resource.Success(Unit)
                    }

                    is Resource.Error -> {
                        delay(1000)
                        _registerState.value = Resource.Error(resource.message ?: "Error desconocido")
                        Log.e("Resource", resource.message.toString())
                        delay(500)
                        _isLoading.value = false
                    }

                    is Resource.Loading -> {
                        _registerState.value = Resource.Loading()
                        Log.e("Resource", "Loading")
                    }
                }
            }
        }
    }
}