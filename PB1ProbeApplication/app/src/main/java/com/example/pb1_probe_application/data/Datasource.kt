package com.example.pb1_probe_application.data

import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.model.ProfilePatientInfo

class Datasource() {

    // TODO: fix state variables
    fun loadProfilePatientInfo(): List<ProfilePatientInfo> {
        return listOf<ProfilePatientInfo>(
            ProfilePatientInfo(R.string.navn,""),
            ProfilePatientInfo(R.string.efternavn,""),
            ProfilePatientInfo(R.string.koen,""),
            ProfilePatientInfo(R.string.alder,""),
            ProfilePatientInfo(R.string.vaegt,""),
            ProfilePatientInfo(R.string.diagnose,""),
            ProfilePatientInfo(R.string.email,""),
            ProfilePatientInfo(R.string.telefon,""),
            ProfilePatientInfo(R.string.empty,"")
        )
    }
}