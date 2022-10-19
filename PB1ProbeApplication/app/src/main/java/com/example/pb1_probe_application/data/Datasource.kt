package com.example.pb1_probe_application.data

import androidx.compose.ui.res.stringResource
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.model.UserInfo

class Datasource() {

    // TODO: fix state variables
    fun loadProfilePatientInfo(): List<UserInfo> {
        return listOf<UserInfo>(
            UserInfo(R.string.navn,""),
            UserInfo(R.string.efternavn,""),
            UserInfo(R.string.koen,""),
            UserInfo(R.string.alder,""),
            UserInfo(R.string.vaegt,""),
            UserInfo(R.string.diagnose,""),
            UserInfo(R.string.email,""),
            UserInfo(R.string.telefon,""),
        )
    }

    // TODO: fix state variables
    fun loadProfileResercherInfo(): List<UserInfo> {
        return listOf<UserInfo>(
            UserInfo(R.string.navn,""),
            UserInfo(R.string.efternavn,""),
            UserInfo(R.string.forskningsenhed,""),
            UserInfo(R.string.email,""),
        )
    }
}