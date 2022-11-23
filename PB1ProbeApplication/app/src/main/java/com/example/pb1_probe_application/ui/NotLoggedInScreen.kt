package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.auth.AuthViewModel
import com.example.pb1_probe_application.data.auth.Resource
import com.example.pb1_probe_application.ui.theme.ButtonColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotLoggedInScreen(logInOnClick: () -> Unit, registerOnClick: () -> Unit, navigateBack: () -> Unit){
        Scaffold(
        topBar = {
            ProbeTopBar(icon = TopBarIcons.Clear, onClick = navigateBack) // TODO - implement onClick navigate back
        },
        content = {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.notLoggedIn), style = Typography.h2)
            Spacer(modifier = Modifier.height(50.dp))
            LoginButton(onClick = logInOnClick, text = R.string.logInd, filled = false)
            Spacer(modifier = Modifier.height(30.dp))
            LoginButton(onClick = registerOnClick, text = R.string.registrer, filled = true)
        }
    }
    )
}
