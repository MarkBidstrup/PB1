package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.ui.theme.Typography


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationsScreen(onClick: () -> Unit ) {

    var checkedPlaceholder1 by remember { mutableStateOf(true) }
    val onCheckedChangePlaceholder1: (Boolean) -> Unit = { checkedPlaceholder1 = !checkedPlaceholder1 }
    var checkedPlaceholder2 by remember { mutableStateOf(true) }
    val onCheckedChangePlaceholder2: (Boolean) -> Unit = { checkedPlaceholder2 = !checkedPlaceholder2 }
    var checkedPlaceholder3 by remember { mutableStateOf(true) }
    val onCheckedChangePlaceholder3: (Boolean) -> Unit = { checkedPlaceholder3 = !checkedPlaceholder3 }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.notificationsHeading), style = Typography.h1) },
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
                backgroundColor = MaterialTheme.colors.onPrimary)
        },
        content = {
            Column() {
                Row() {
                Text(
                    text = stringResource(R.string.push),
                    style = Typography.body1,
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                )
                Row(Modifier.fillMaxWidth().padding(end = 10.dp), horizontalArrangement = Arrangement.End) {
                    Switch(
                        checked = checkedPlaceholder1,
                        onCheckedChange = onCheckedChangePlaceholder1,
                        modifier = Modifier.wrapContentSize(),
                    )
                }
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp)
                )
                Row() {
                    Text(
                        text = stringResource(R.string.email),
                        style = Typography.body1,
                        modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                    )
                    Row(Modifier.fillMaxWidth().padding(end = 10.dp), horizontalArrangement = Arrangement.End) {
                        Switch(
                            checked = checkedPlaceholder2,
                            onCheckedChange = onCheckedChangePlaceholder2,
                            modifier = Modifier.wrapContentSize(),
                        )
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp)
                )
                Row() {
                    Text(
                        text = stringResource(R.string.sms),
                        style = Typography.body1,
                        modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                    )
                    Row(Modifier.fillMaxWidth().padding(end = 10.dp), horizontalArrangement = Arrangement.End) {
                        Switch(
                            checked = checkedPlaceholder3,
                            onCheckedChange = onCheckedChangePlaceholder3,
                            modifier = Modifier.wrapContentSize(),
                        )
                    }
                }
                Divider(
                    thickness = 1.dp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp)
                )
                Text(
                    text = stringResource(R.string.faaNotiPååEmailOgSms),
                    style = Typography.caption,
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                )
            }
        }
    )
}

