package com.project.kotlincomposeapp.ui.viewsModels

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _numberPhone = MutableLiveData<String>()
    val numberPhone: LiveData<String> = _numberPhone

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onRegisterChanged(email: String, password: String, numberPhone: String, username: String) {
        _email.value = email
        _password.value = password
        _numberPhone.value = numberPhone
        _username.value = username
        _loginEnable.value = isValidEmail(email) && isValidPassword(password) && isValidNumberPhone(numberPhone) && isValidUsername(username)
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length > 6

    private fun isValidNumberPhone(numberPhone: String): Boolean = Patterns.PHONE.matcher(numberPhone).matches()

    private fun isValidUsername(username: String): Boolean = username.length > 4

    fun onLoginSelected(){
        _isLoading.value = true
    }

    fun resetLoading() {
        _isLoading.value = false
    }
}