package com.example.pb1_probe_application.model

import androidx.annotation.StringRes
import com.example.pb1_probe_application.data.UserInfoState

data class UserInfo(
    @StringRes val StringResourceHeaderId: Int,
    val profileInfoState: String
)
