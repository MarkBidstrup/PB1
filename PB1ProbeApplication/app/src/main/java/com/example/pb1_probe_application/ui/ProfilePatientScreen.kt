package com.example.pb1_probe_application.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
//    ScaffoldDemo()
}

@Composable
fun PatientInfoList(patientInfoList: List<ProfilePatientInfo>, modifier: Modifier = Modifier) {

//    Scaffold() {
//        TopAppBar() {
//            Text(
//                text = "Min profil", // TODO: hardcoded and missing things
//                style = MaterialTheme.typography.h1
//            )
//        }
//        LazyColumn {
//            items(patientInfoList) { ProfilePatientInfo ->
//                PatientInfoField(ProfilePatientInfo)
//            }
//        }
//        BottomAppBar(backgroundColor = NavBarColorGreen) {
//            Text(
//                text = "Placeholder for nav bar",
//                style = MaterialTheme.typography.h1,
//            )
//        }
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Min profil", style = Typography.h1)
                        },
                backgroundColor = androidx.compose.ui.graphics.Color.White)
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

@Composable
fun ScaffoldDemo() {
    val materialBlue700= androidx.compose.ui.graphics.Color.Blue
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Open))
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(title = {Text("TopAppBar")},backgroundColor = materialBlue700)  },
        content = { Text("BodyContent") },
        bottomBar = { BottomAppBar(backgroundColor = materialBlue700) { Text("BottomAppBar") } }
    )
}

@Preview
@Composable
private fun AffirmationCardPreview() {
    ProfilePatienScreen()
}