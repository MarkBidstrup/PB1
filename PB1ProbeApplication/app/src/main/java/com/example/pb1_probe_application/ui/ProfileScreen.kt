package com.example.pb1_probe_application.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.model.UserInfo
import com.example.pb1_probe_application.ui.theme.NavBarColorGreen
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun ProfileScreen(user: String) {
    val currentUser: String = user

    if (currentUser.equals("patient"))
        PatientInfoList(userInfoList = Datasource().loadProfilePatientInfo())
    if (currentUser.equals("researcher"))
        PatientInfoList(userInfoList = Datasource().loadProfileResercherInfo())
}

@Composable
fun PatientInfoList(userInfoList: List<UserInfo>, modifier: Modifier = Modifier) {



    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.MyProfileHeader), style = Typography.h1) },
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
                        items(userInfoList) { ProfilePatientInfo ->
                            PatientInfoField(ProfilePatientInfo)
                        }

                    }
                  },
        bottomBar = {
            BottomAppBar(
                backgroundColor = NavBarColorGreen
            ) {
                Text(stringResource(R.string.Placeholder))
            }
        }
    )

}

@Composable
fun PatientInfoField(patientInfo: UserInfo, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = LocalContext.current.getString(patientInfo.StringResourceHeaderId),
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Text(
            text = stringResource(R.string.Placeholder), // TODO: insert variable text here
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.body1,
            color = androidx.compose.ui.graphics.Color.Black
        )
        Divider(
            modifier = Modifier.padding(10.dp),
            thickness = 1.dp,
            color = androidx.compose.ui.graphics.Color.LightGray
        )
    }
}

@Preview
@Composable
private fun ProfilePatientScreenPreview() {
    PatientInfoList(userInfoList = Datasource().loadProfilePatientInfo())
}