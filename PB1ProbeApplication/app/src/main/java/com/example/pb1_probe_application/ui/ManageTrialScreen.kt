package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.ui.theme.TextColorRed
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ManageTrialScreen(trialsViewModel: TrialsViewModel, navBack: () -> Unit, onClickNavToEditTrial: () -> Unit, onClickNav:() -> Unit, navDelete: () -> Unit) {

    Scaffold(

        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.håndterStudie), style = Typography.h1) },
                navigationIcon = {
                    IconButton(
                        onClick = navBack
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
                    text = stringResource(id = R.string.redigerStudie),
                    style = Typography.body1,
                    modifier = Modifier
                        .padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                        .clickable { onClickNavToEditTrial() }
                )
                Divider(
                    thickness = 1.dp,
                    color = androidx.compose.ui.graphics.Color.LightGray,
                    modifier = Modifier.padding(start = 17.dp, end = 17.dp)
                )
                Text(
                    text = stringResource(R.string.seDeltagerliste),
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
                    text = stringResource(R.string.sletStudie),
                    style = Typography.body1,
                    color = TextColorRed,
                    modifier = Modifier
                        .padding(start = 17.dp, end = 17.dp, bottom = 10.dp, top = 10.dp)
                        .clickable {
                            navDelete()
                        }
                )
            }
        }
    )
}