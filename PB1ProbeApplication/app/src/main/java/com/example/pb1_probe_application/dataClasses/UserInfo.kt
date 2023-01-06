package com.example.pb1_probe_application.dataClasses

import androidx.annotation.StringRes

data class UserInfo(
    @StringRes val StringResourceHeaderId: Int,
    val userInfoType: UserInfoTypes,
)

enum class UserInfoTypes {
    FirstName, LastName, Gender, Age, Weight, Diagnosis, Email, Phone, Department
}
