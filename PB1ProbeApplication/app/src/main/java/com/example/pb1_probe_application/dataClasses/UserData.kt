package com.example.pb1_probe_application.dataClasses

sealed class UserData(
    val role: Role,
    var name: String = "",
    var lastName: String = "",
    var email: String = "",
    var phone: String = "",
)

data class UserPatient (
    var gender: String = "",
    var age: String = "",
    var weight: String = "",
    var diagnosis: String = "",
): UserData(Role.TRIAL_PARTICIPANT)

data class UserResearcher (
    var department: String = "",
    var job: String = "",
): UserData(Role.RESEARCHER)
