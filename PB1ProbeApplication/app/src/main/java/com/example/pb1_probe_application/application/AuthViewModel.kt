package com.example.pb1_probe_application.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pb1_probe_application.data.auth.AuthRepository
import com.example.pb1_probe_application.data.auth.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// code from: https://www.youtube.com/watch?v=LHh2_TXBmS8&t=1662s&ab_channel=SimplifiedCoding

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow
    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow
    private val _resetPasswordFlow = MutableStateFlow<Resource<Boolean>?>(null)
    val resetPasswordFlow: StateFlow<Resource<Boolean>?> = _resetPasswordFlow
    private val _updateEmailFlow = MutableStateFlow<Resource<Boolean>?>(null)
    val updateEmailFlow: StateFlow<Resource<Boolean>?> = _updateEmailFlow
    val currentUser: FirebaseUser?
        get() = repository.currentUser
    var forgottenEmail: String = "" // for navigation

    // from: https://www.youtube.com/watch?v=LHh2_TXBmS8&t=1662s&ab_channel=SimplifiedCoding
    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(email,password)
        _loginFlow.value = result
    }

    // from: https://www.youtube.com/watch?v=LHh2_TXBmS8&t=1662s&ab_channel=SimplifiedCoding
    fun signup(email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = Resource.Loading
        val result = repository.signup(email,password)
        _signupFlow.value = result
    }

    // from: https://www.youtube.com/watch?v=LHh2_TXBmS8&t=1662s&ab_channel=SimplifiedCoding
    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }

    fun resetPassword(email: String) = viewModelScope.launch {
        _resetPasswordFlow.value = Resource.Loading
        val result = repository.forgotPassword(email)
        _resetPasswordFlow.value = result
    }


    fun delete() {
        repository.delete()
        _loginFlow.value = null
        _signupFlow.value = null
    }

    fun updateEmail(email: String) = viewModelScope.launch {
        _updateEmailFlow.value = Resource.Loading
        val result = repository.updateEmail(email)
        _updateEmailFlow.value = result
    }
}