package com.example.pb1_probe_application.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pb1_probe_application.data.userData.UserDataRepository
import com.example.pb1_probe_application.dataClasses.Role
import com.example.pb1_probe_application.dataClasses.UserData
import com.example.pb1_probe_application.dataClasses.UserPatient
import com.example.pb1_probe_application.dataClasses.UserResearcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserDataRepository,

    ): ViewModel() {

    private val _userDataFlow = MutableStateFlow<UserData?>(null)
    val userDataFlow: StateFlow<UserData?> = _userDataFlow.asStateFlow()
    private val _otherUserDataFlow = MutableStateFlow<UserData?>(null)
    val otherUserDataFlow: StateFlow<UserData?> = _otherUserDataFlow.asStateFlow()
    private val _multiUserDataFlow = MutableStateFlow<List<UserData>>(emptyList())
    val multiUserDataFlow: StateFlow<List<UserData>> = _multiUserDataFlow.asStateFlow()
    lateinit var currentUserID: String
    lateinit var currentUserData: UserData

    fun saveUserData(userID: String, data: UserData) = viewModelScope.launch {
        repository.update(userID, data)
    }

    fun createUser(userID: String, email: String, role: Role) = viewModelScope.launch  {
        val user: UserData = if (role == Role.RESEARCHER) {
            UserResearcher()
        } else {
            UserPatient()
        }
        user.email = email
        repository.addNew(userID, user)
    }

    fun getViewModelUserData(userID: String) = viewModelScope.launch {
        _otherUserDataFlow.value = repository.getData(userID)
    }

    fun getViewModelMultiUserData(userIDs: List<String>) = viewModelScope.launch {
        _multiUserDataFlow.value = repository.getData(userIDs)
    }

    fun deleteUser(userID: String) = viewModelScope.launch {
        repository.delete(userID)
    }

    fun getUserRole(): Role? {
        val result = userDataFlow.value?.role
        return result
    }

    fun setCurrentUser(userID: String) = viewModelScope.launch {
        currentUserID = userID
        currentUserData = repository.getData(userID)
        _userDataFlow.value = repository.getData(currentUserID)
    }
//    public override fun onCleared() {
//        super.onCleared()
//    }



}
