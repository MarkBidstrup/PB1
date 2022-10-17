package com.example.pb1_probe_application.ui

import android.graphics.Paint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.model.ProfilePatientInfo
import com.example.pb1_probe_application.ui.theme.NavBarColorGreen
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun ProfilePatienScreen() {
    PatientInfoList(patientInfoList = Datasource().loadProfilePatientInfo())
}

@Composable
fun PatientInfoList(patientInfoList: List<ProfilePatientInfo>, modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text("Min profil", style = Typography.h1) },
                backgroundColor = androidx.compose.ui.graphics.Color.White)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                 IconButton(onClick = { /*TODO*/ }) {
                     Icon(
                         Icons.Default.ArrowBack,
                         contentDescription = "back arrow",
                     )
                 }
            }
                 },
        content = {
                    LazyColumn {
            items(patientInfoList) { ProfilePatientInfo ->
                PatientInfoField(ProfilePatientInfo)
            }
        }
                  },
        bottomBar = {
            BottomAppBar(
                backgroundColor = NavBarColorGreen
            ) {
                Text("Placeholder")
            }
        }
    )

}

@Composable
fun PatientInfoField(patientInfo: ProfilePatientInfo, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = LocalContext.current.getString(patientInfo.StringResourceHeaderId),
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Text(
            text = "Placeholder", // TODO: insert variable text here
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.body1,
            color = androidx.compose.ui.graphics.Color.Black
        )
        Divider(
            startIndent = 10.dp,
            thickness = 1.dp,
            color = androidx.compose.ui.graphics.Color.LightGray)
    }
}

@Preview
@Composable
private fun AffirmationCardPreview() {
    ProfilePatienScreen()
}