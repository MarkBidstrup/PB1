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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.dataClasses.*
import com.example.pb1_probe_application.navigation.BottomBar
import com.example.pb1_probe_application.ui.theme.TextColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun ProfileScreen(role: Role, navHostController: NavHostController, userViewModel: UserViewModel) {
    val currentUser: Role = role
    if (currentUser == Role.TRIAL_PARTICIPANT)
        UserInfoList(userInfoList = Datasource().loadProfilePatientInfo(), navHostController = navHostController, userViewModel = userViewModel)
    if (currentUser == Role.RESEARCHER)
        UserInfoList(userInfoList = Datasource().loadProfileResearcherInfo(), navHostController = navHostController, userViewModel = userViewModel)
}

@Composable
fun UserInfoList(userInfoList: List<UserInfo>, navHostController: NavHostController, userViewModel: UserViewModel) {

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
            val userData = userViewModel.currentUserData
            LazyColumn (modifier = Modifier.padding(padding)) {
                items(userInfoList) { UserInfo ->
                    var content = ""
                    if (userData is UserPatient) {
                        when (UserInfo.userInfoType) {
                            UserInfoTypes.FirstName -> content = userData.name
                            UserInfoTypes.LastName -> content = userData.lastName
                            UserInfoTypes.Gender -> content = userData.gender
                            UserInfoTypes.Age -> content = userData.age
                            UserInfoTypes.Weight -> content = userData.weight + " kg"
                            UserInfoTypes.Diagnosis -> content = userData.diagnosis
                            UserInfoTypes.Email -> content = userData.email
                            UserInfoTypes.Phone -> content = userData.phone
                            else -> {}
                        }
                    }
                    if (userData is UserResearcher) {
                        when (UserInfo.userInfoType) {
                            UserInfoTypes.FirstName -> content = userData.name
                            UserInfoTypes.LastName -> content = userData.lastName
                            UserInfoTypes.Email -> content = userData.email
                            UserInfoTypes.Phone -> content = userData.phone
                            UserInfoTypes.Department -> content = userData.department
                            else -> {}
                        }
                    }
                    UserInfoField(UserInfo, content)
                    if (userInfoList.lastIndexOf(element = UserInfo) != userInfoList.lastIndex) {
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
fun UserInfoField(userInfo: UserInfo, content: String) {
    Column {
        Text(
            text = LocalContext.current.getString(userInfo.StringResourceHeaderId),
            modifier = Modifier.padding(start = 17.dp),
            style = MaterialTheme.typography.body1,
            color = TextColorGreen
        )
        Text(
            text = content,
            modifier = Modifier.padding(start = 17.dp),
            style = MaterialTheme.typography.body1,
        )
    }
}

