package com.example.pb1_probe_application.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.model.ProfilePatientInfo

@Composable
fun ProfilePatienScreen() {
    PatientInfoList(patientInfoList = Datasource().loadProfilePatientInfo())
}

@Composable
fun PatientInfoList(patientInfoList: List<ProfilePatientInfo>, modifier: Modifier = Modifier) {
    Text(
        text = "Min profil", // TODO: insert variable text here
        style = MaterialTheme.typography.h1,
        )
    LazyColumn {
        items(patientInfoList) { ProfilePatientInfo ->
            PatientInfoField(ProfilePatientInfo)
        }
    }
}

@Composable
fun PatientInfoField(patientInfo: ProfilePatientInfo, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = LocalContext.current.getString(patientInfo.StringResourceHeaderId),
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.body1,
            color = androidx.compose.ui.graphics.Color.Green
        )
        Text(
            text = "Placeholder", // TODO: insert variable text here
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.body1,
            color = androidx.compose.ui.graphics.Color.Green
        )
        Divider(
            startIndent = 8.dp,
            thickness = 1.dp,
            color = androidx.compose.ui.graphics.Color.Black)
    }
}

@Preview
@Composable
private fun AffirmationCardPreview() {
    ProfilePatienScreen()
}