package com.example.pb1_probe_application.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.ui.theme.NavBarColorGreen
import com.example.pb1_probe_application.ui.theme.TextColorRed
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun SettingsScreen(user: String) {
    val currentUser: String = user

    if (currentUser.equals("patient"))
        SettingsPatientScreen()
    if (currentUser.equals("researcher"))
        SettingsResearcherScreen()
}

@Composable
fun SettingsPatientScreen() {
    var checkedPlaceholder: Boolean = true;
    var onCheckedChangePlaceholder: (Boolean) -> Unit = { checkedPlaceholder = it };

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.settingsHeading), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back arrow"
                    )
                }
            }
        },
        content = {
            Column() {
                Row() {
                    Column() {
                        Text(
                            text = stringResource(R.string.tilgaengelighed),
                            style = Typography.body1,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                        Text(
                            text = stringResource(R.string.forskereKanAnmode),
                            style = Typography.body2,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
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
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = stringResource(R.string.notifikationer),
                    style = Typography.body1,
                    modifier = Modifier.padding(10.dp)
                )
                Divider(
                    thickness = 1.dp,
                    color = androidx.compose.ui.graphics.Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = stringResource(R.string.privatLicensAftale),
                    style = Typography.body1,
                    modifier = Modifier.padding(10.dp)
                )
                Divider(
                    thickness = 1.dp,
                    color = androidx.compose.ui.graphics.Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = stringResource(R.string.logUd),
                    style = Typography.body1,
                    color = TextColorRed,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    )
}

@Composable
fun SettingsResearcherScreen() {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.settingsHeading), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back arrow"
                    )
                }
            }
        },
        content = {
            Column() {
                Text(
                    text = stringResource(R.string.notifikationer),
                    style = Typography.body1,
                    modifier = Modifier.padding(10.dp)
                )
                Divider(
                    thickness = 1.dp,
                    color = androidx.compose.ui.graphics.Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = stringResource(R.string.privatLicensAftale),
                    style = Typography.body1,
                    modifier = Modifier.padding(10.dp)
                )
                Divider(
                    thickness = 1.dp,
                    color = androidx.compose.ui.graphics.Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = stringResource(R.string.logUd),
                    style = Typography.body1,
                    color = TextColorRed,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    )
}