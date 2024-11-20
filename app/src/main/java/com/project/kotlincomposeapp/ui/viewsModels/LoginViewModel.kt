package com.project.kotlincomposeapp.ui.viewsModels

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.kotlincomposeapp.domain.model.Resource
import com.project.kotlincomposeapp.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.delay

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
): ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginState: MutableStateFlow<Resource<Unit>> = MutableStateFlow(Resource.Loading())
    val loginState: StateFlow<Resource<Unit>> get() = _loginState

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidUsername(email) && isValidPassword(password)
    }

    private fun isValidUsername(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    fun onLoginSelected(email: String, password: String){
        _isLoading.value = true
        viewModelScope.launch {
            loginUseCase(email, password).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _loginState.value = Resource.Success(Unit)
                        Log.e("Resource", "Success")
                    }

                    is Resource.Error -> {
                        delay(1000)
                        _loginState.value = Resource.Error(resource.message ?: "Error desconocido")
                        Log.e("Resource", resource.message.toString())
                        delay(500)
                        _isLoading.value = false
                    }

                    is Resource.Loading -> {
                        _loginState.value = Resource.Loading()
                        Log.e("Resource", "Loading")
                    }
                }

            }
        }
    }

    fun resetLoginState() {
        _loginState.value = Resource.Loading()
    }
}