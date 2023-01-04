package com.example.pb1_probe_application.dataClasses

import androidx.annotation.StringRes

data class UserInfo(
    @StringRes val StringResourceHeaderId: Int,
    val profileInfo: userInfoAttributes,
    var uiData: String
)

enum class userInfoAttributes {
    firstName, lastName, gender, age, weight, diagnosis, email, tlf, institute
}
