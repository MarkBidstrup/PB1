package com.example.pb1_probe_application.ui

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.NonNull
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.model.BottomBarItems
import com.example.pb1_probe_application.model.BottomBarItems.Home.route

import com.example.pb1_probe_application.model.Role
import com.example.pb1_probe_application.model.Route

import com.example.pb1_probe_application.ui.theme.TextColorRed
import com.example.pb1_probe_application.ui.theme.Typography


@Composable
fun SettingsScreen(role: Role) {
    val currentUser: Role = role



    if (currentUser.equals(Role.TRIAL_PARTICIPANT))
        SettingsPatientScreen()
    if (currentUser.equals(Role.RESEARCHER))
        SettingsResearcherScreen()
}

@Composable
fun SettingsPatientScreen() {

//    val navController = rememberNavController()
//    Navigation(navController = navController)

    var checkedPlaceholder: Boolean = true;
    var onCheckedChangePlaceholder: (Boolean) -> Unit = { checkedPlaceholder = it };

    Scaffold(


        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.settingsHeading), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(onClick =  {
                    //TODO: implement onClick
//                  navController.popBackStack()


                }) {
                    Icon(

                        Icons.Default.ArrowBack,
                        contentDescription = "back arrow"
                    )
                }
            }
        },
        content = {
//            val navHostController = rememberNavController()
            Column() {
                Row() {
                    Column() {

                        Text(
                            text = stringResource(R.string.tilgaengelighed),
                            style = Typography.body1,
                            modifier = Modifier.padding(start = 17.dp, end = 17.dp)
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
                            .padding(end = 10.dp), horizontalArrangement = Arrangement.End) {
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
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
//                        .clickable {
//                            navController.navigate(Route.Notification.route) {
//                                popUpTo(navController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
//
//                                launchSingleTop = true
//                                restoreState = true
//
//                            }
//
//
//                        }

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
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                )
            }
        }
    )
}



@Composable
fun SettingsResearcherScreen() {
//    val navController = rememberNavController()
//    Navigation(navController = navController)

    Scaffold(

        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.settingsHeading), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {
//                   navController.popBackStack()





                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back arrow"
                    )
                }
            }
        },
        content = {
            Column() {
//                val navHostController = rememberNavController()
                Text(
                    text = stringResource(R.string.notifikationer),
                    style = Typography.body1,
                    modifier = Modifier
                        .padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
//                        .clickable {
//                            navHostController.navigate(Route.Notification.route) {
//                                popUpTo(navHostController.graph.findStartDestination().id) {
//                                    saveState = true
//                                }
//
//                                launchSingleTop = true
//                                restoreState = true
//
//                            }
//
//
//                        }
                )
                Divider(
                    thickness = 1.dp,
                    color = androidx.compose.ui.graphics.Color.LightGray,
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp)
                )
                Text(
                    text = stringResource(R.string.privatLicensAftale),
                    style = Typography.body1,
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
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
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                        //log ud
//                        .clickable {
//                            navController.navigate("Home") {
//                                popUpTo(route="Home") {
//                                    inclusive = true
//                                }
//                            }
//                        }
                )
            }
        }
    )
}
// har prøvet at lave en anden navHost for at fikse navigation men :(
//@Composable
//fun Navigation(navController: NavHostController) {
//    NavHost(navController = navController,
//        startDestination = Route.Setting.route
//    ) {
//        composable( route = Route.Setting.route) {
//            SettingsScreen(role = Role.TRIAL_PARTICIPANT)
//
//        }
//    }
//}

@Preview
@Composable
fun SettingsScreenPreview() {

    SettingsPatientScreen()

}
