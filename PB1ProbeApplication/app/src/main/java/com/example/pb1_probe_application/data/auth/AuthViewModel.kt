package com.example.pb1_probe_application.data.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow
    val currentUser: FirebaseUser?
        get() = repository.currentUser

//    init {
//        if (repository.currentUser != null) {
//            _loginFlow.value = Resource.Success(repository.currentUser!!)
//        }
//    }

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = repository.login(email,password)
        _loginFlow.value = result
    }

    fun signup(email: String, password: String) = viewModelScope.launch {
        _signupFlow.value = Resource.Loading
        val result = repository.signup(email,password)
        _signupFlow.value = result
    }

    fun logout() {
        repository.logout()
        _loginFlow.value = null
        _signupFlow.value = null
    }
}