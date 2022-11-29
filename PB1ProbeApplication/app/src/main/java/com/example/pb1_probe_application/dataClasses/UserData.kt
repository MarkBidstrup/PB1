package com.example.pb1_probe_application.dataClasses

sealed class UserData(
    val userType: Role,
    var name: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = "",
)

data class UserPatient (
    //@DocumentId var userID: String,
    var gender: String = "",
    var age: Int = 0,
    var weight: Double = 0.0,
    var diagnosis: String = "",
): UserData(Role.TRIAL_PARTICIPANT)

data class UserResearcher (
    //@DocumentId var userID: String,
    var department: String = "",
    var job: String = "",
): UserData(Role.RESEARCHER)
