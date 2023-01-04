package com.example.pb1_probe_application.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pb1_probe_application.data.userData.UserDataRepository
import com.example.pb1_probe_application.dataClasses.Role
import com.example.pb1_probe_application.dataClasses.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserDataRepository,

) : ViewModel() {
    private val _userDataFlow = MutableStateFlow<UserData?>(null)
    val userDataFlow: StateFlow<UserData?> = _userDataFlow.asStateFlow()
    lateinit var currentUserID: String

    fun saveUserData(userID: String, data: UserData) = viewModelScope.launch {
        repository.update(userID, data)
    }

    fun getViewModelUserData() = viewModelScope.launch {
        _userDataFlow.value = repository.getData(currentUserID)
    }

    fun deleteUser(userID: String) = viewModelScope.launch {
        repository.delete(userID)
    }

    fun getUserRole(): Role? {
        val result = userDataFlow.value?.userType
        return result
    }

    fun setCurrentUser(userID: String) {
        currentUserID = userID
    }
}
