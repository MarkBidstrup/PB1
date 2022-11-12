package com.example.pb1_probe_application.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.ui.theme.PB1ProbeApplicationTheme
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun AppliedScreen(navHostController: NavHostController = rememberNavController()) {
    Scaffold(
        topBar = {
                 ProbeTopBar()
        },
        content = {
            Column(modifier = Modifier
                .padding(17.dp)
                .padding(bottom = 46.dp)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally){
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.width(110.dp)) {
                    Image(painter = painterResource(R.drawable.green_checkmark),
                        contentDescription = "checkmark",
                        contentScale = ContentScale.Fit)
                }
                Text(text = stringResource(R.string.ansoegt), style = Typography.h2,
                    textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.weight(1.5f))
            }
        },
        bottomBar = {
            BottomBar(navController = navHostController)
        }
    )
}

@Preview
@Composable
fun AppliedScreenPreview() {
    PB1ProbeApplicationTheme(darkTheme = false) {
        AppliedScreen()
    }
}
