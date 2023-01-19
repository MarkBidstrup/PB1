package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.*

import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.UserViewModel


import com.example.pb1_probe_application.dataClasses.Role

import com.example.pb1_probe_application.ui.theme.TextColorRed
import com.example.pb1_probe_application.ui.theme.Typography



@Composable
fun SettingsScreen(role: Role, onClick: () -> Unit, onClickNav :() -> Unit, authViewModel: AuthViewModel?, userViewModel: UserViewModel, logOutNav :() -> Unit) {
    val currentUser: Role = role
    if (currentUser.equals(Role.TRIAL_PARTICIPANT))
        SettingsPatientScreen(onClick = onClick, onClickNav = onClickNav, authViewModel = authViewModel,logOutNav = logOutNav, userViewModel = userViewModel)
    if (currentUser.equals(Role.RESEARCHER))
        SettingsResearcherScreen(onClick = onClick, onClickNav = onClickNav, authViewModel = authViewModel,logOutNav = logOutNav, userViewModel = userViewModel)
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsPatientScreen(onClick: () -> Unit, onClickNav :() -> Unit, authViewModel: AuthViewModel?, userViewModel: UserViewModel, logOutNav :() -> Unit) {
    var checkedPlaceholder by remember { mutableStateOf(true) }
    val onCheckedChangePlaceholder: (Boolean) -> Unit = { checkedPlaceholder = !checkedPlaceholder }
    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.settingsHeading), style = Typography.h1) },
                navigationIcon = {
                    IconButton(
                        onClick = { onClick() }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.onPrimary
            )
        }
    ) {

        Column() {
            Row() {
                Column() {
                    Text(
                        text = stringResource(R.string.tilgaengelighed),
                        style = Typography.body1,
                        modifier = Modifier.padding(start = 17.dp, end = 17.dp, top = 10.dp)
                    )
                    Text(
                        text = stringResource(R.string.forskereKanAnmode),
                        style = Typography.body2,
                        modifier = Modifier.padding(start = 17.dp, end = 17.dp)
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp), horizontalArrangement = Arrangement.End
                ) {
                    Switch(
                        checked = checkedPlaceholder,
                        onCheckedChange = onCheckedChangePlaceholder,
                        modifier = Modifier.wrapContentSize(),
                    )
                }
            }
            Divider(
                thickness = 1.dp,
                color = androidx.compose.ui.graphics.Color.LightGray,
                modifier = Modifier.padding(start = 17.dp, end = 17.dp)
            )
            Text(

                text = stringResource(R.string.notifikationer),
                style = Typography.body1,
                modifier = Modifier
                    .padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                    .clickable {
                        onClickNav()
                    }
            )
            Divider(
                thickness = 1.dp,
                color = androidx.compose.ui.graphics.Color.LightGray,
                modifier = Modifier.padding(start = 17.dp, end = 17.dp)
            )
            Text(
                text = stringResource(R.string.privatLicensAftale),
                style = Typography.body1,
                modifier = Modifier
                    .padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                    .clickable {
                        val url = "https://probe.dk/privatlivspolitik/"
                        CustomTabsIntent
                            .Builder()
                            .build()
                            .launchUrl(context, Uri.parse(url))
                    }
            )
            Divider(
                thickness = 1.dp,
                color = androidx.compose.ui.graphics.Color.LightGray,
                modifier = Modifier.padding(start = 17.dp, end = 17.dp)
            )
            Text(
                text = stringResource(R.string.logUd),
                style = Typography.body1,
                color = TextColorRed,
                modifier = Modifier
                    .padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                    .clickable {
                        authViewModel?.logout()
                        userViewModel.resetUserRole()
                        logOutNav()
                    }
            )
        }
    }
}
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsResearcherScreen(onClick: () -> Unit, onClickNav :() -> Unit, authViewModel: AuthViewModel?, userViewModel: UserViewModel, logOutNav :() -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.settingsHeading), style = Typography.h1) },
                navigationIcon = {
                    IconButton(
                        onClick = { onClick() }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.onPrimary
            )
        },
        content = {
            Column() {

                Text(
                    text = stringResource(R.string.notifikationer),
                    style = Typography.body1,
                    modifier = Modifier
                        .padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                        .clickable {
                            onClickNav()
                        }
                )
                Divider(
                    thickness = 1.dp,
                    color = androidx.compose.ui.graphics.Color.LightGray,
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp)
                )
                Text(
                    text = stringResource(R.string.privatLicensAftale),
                    style = Typography.body1,
                    modifier = Modifier
                        .padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                        .clickable {
                            val url = "https://probe.dk/privatlivspolitik/"
                            CustomTabsIntent
                                .Builder()
                                .build()
                                .launchUrl(context, Uri.parse(url))
                        }
                )
                Divider(
                    thickness = 1.dp,
                    color = androidx.compose.ui.graphics.Color.LightGray,
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp)
                )
                Text(
                    text = stringResource(R.string.logUd),
                    style = Typography.body1,
                    color = TextColorRed,
                    modifier = Modifier
                        .padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                        .clickable {
                            authViewModel?.logout()
                            userViewModel.resetUserRole()
                            logOutNav()
                        }
                )
            }
        }
    )
}


