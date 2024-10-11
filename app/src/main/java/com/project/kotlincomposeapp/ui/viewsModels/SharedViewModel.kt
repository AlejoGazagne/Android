/*package com.project.kotlincomposeapp.ui.viewsModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _age = MutableLiveData<String>()
    val age: LiveData<String> get() = _age

    fun setData(email: String, password: String) {
        _email.value = email
        _password.value = password
    }

    fun clearData() {
        _email.value = ""
        _password.value = ""
        _username.value = ""
        _age.value = ""
    }
}*/