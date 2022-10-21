package com.example.pb1_probe_application.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.ui.theme.TextColorGrey
import com.example.pb1_probe_application.ui.theme.TextColorRed
import com.example.pb1_probe_application.ui.theme.Typography


@Composable
fun NotificationsScreen() {
    var checkedPlaceholder: Boolean = true;
    var onCheckedChangePlaceholder: (Boolean) -> Unit = { checkedPlaceholder = it };

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.notificationsHeading), style = Typography.h1) },
                backgroundColor = Color.White)
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
                Text(
                    text = stringResource(R.string.push),
                    style = Typography.body1,
                    modifier = Modifier.padding(10.dp)
                )
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
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Row() {
                    Text(
                        text = stringResource(R.string.email),
                        style = Typography.body1,
                        modifier = Modifier.padding(10.dp)
                    )
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
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Row() {
                    Text(
                        text = stringResource(R.string.sms),
                        style = Typography.body1,
                        modifier = Modifier.padding(10.dp)
                    )
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
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                )
                Text(
                    text = stringResource(R.string.faaNotiPååEmailOgSms),
                    style = Typography.caption,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    )
}