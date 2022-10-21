package com.example.pb1_probe_application.data

import androidx.compose.ui.res.stringResource
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.model.TrialState
import com.example.pb1_probe_application.model.UserInfo
import java.util.*

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

    // TODO: fix/replace dummy data
    fun loadTrials(): List<TrialState> {
        return listOf<TrialState>(
            TrialState("Tidlige abnormiteter i nethinden ved diabetes","At undersøge tidlige forandringer i nethinden, som kan relateres til diabetes",
                    Date(2022,11,22),200,"Diabetikere", "Børn under 3", 200,
            "Herlev Hospital", false, false, "32 dage", 3, "30 minutter", Date(2022,12,22),
                    Date(2023,1,22), listOf<String>("Diabetes type 1", "Diabetes type 2"),13,142,
                    "Jens Larsen", "Herlev Hospital", "+45 43562343"
            ),
            TrialState("Forsøgsnavn","Formål", Date(2023,1,22),20,"Inklusion", "Eksklusion", 0,
                "Hvidovre Hospital", true, false, "10 dage", 1, "1 time", Date(2023,2,22),
                Date(2023,3,22), listOf<String>("Diagnose 1", "Diagnose 2"),4,20,
                "Jens Larsen", "Herlev Hospital", "+45 43562343"
            ),
        )
    }
}