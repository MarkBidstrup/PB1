package com.example.pb1_probe_application.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.model.Role
import com.example.pb1_probe_application.model.UserInfo
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun ProfileScreen(role: Role, navHostController: NavHostController) {
    val currentUser: Role = role

    if (currentUser.equals(Role.TRIAL_PARTICIPANT))
        UserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), navHostController = navHostController)
    if (currentUser.equals(Role.RESEARCHER))
        UserInfoList(userInfoList = Datasource().loadProfileResearcherInfo(), navHostController = navHostController)
}

@Composable
fun UserInfoList(userInfoList: List<UserInfo>, modifier: Modifier = Modifier, navHostController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.myProfileHeading), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary,
                elevation = 0.dp)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                 IconButton(onClick = {
                     navHostController.navigate("EditProfile")
                 }) {
                     Icon(
                         Icons.Default.Edit,
                         contentDescription = "edit",
                     )
                 }
                IconButton(onClick = {
                    // to navigate
                   navHostController.navigate("Setting")

               }) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "settings",
                    )
                }
            }
                 },
        content = { padding ->
                    LazyColumn (modifier = Modifier.padding(padding)) {
                        items(userInfoList) { UserInfo ->
                            UserInfoField(UserInfo)
                            if (!(userInfoList.lastIndexOf(element = UserInfo) == userInfoList.lastIndex)) {
                                Divider(
                                    modifier = Modifier.padding(start = 17.dp, top = 10.dp, bottom = 10.dp, end = 17.dp),
                                    thickness = 1.dp,
                                    color = androidx.compose.ui.graphics.Color.LightGray
                                )
                            }
                        }
                    }
                  },
        bottomBar = {
            BottomBar(navController = navHostController)
        }
    )
}

@Composable
fun UserInfoField(userInfo: UserInfo, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = LocalContext.current.getString(userInfo.StringResourceHeaderId),
            modifier = Modifier.padding(start = 17.dp),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Text(
            text = stringResource(R.string.placeholder), // TODO: insert variable text here
            modifier = Modifier.padding(start = 17.dp),
            style = MaterialTheme.typography.body1,
        )
    }
}

@Preview
@Composable
private fun ProfileUserScreenPreview() {
//    UserInfoList(userInfoList = Datasource().loadProfilePatientInfo())
}
