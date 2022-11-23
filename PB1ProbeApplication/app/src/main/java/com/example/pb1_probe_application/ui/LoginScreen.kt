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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.auth.AuthViewModel
import com.example.pb1_probe_application.data.auth.Resource
import com.example.pb1_probe_application.ui.theme.ButtonColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LogInScreen(navHostController: NavHostController?, authViewModel: AuthViewModel?, navigateBack: () -> Unit){

    // changes for login
    val loginFlow = authViewModel?.loginFlow?.collectAsState()

    var email by remember { mutableStateOf("test@test.com") }
    var password by remember { mutableStateOf("123456") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            ProbeTopBar(icon = TopBarIcons.Clear, onClick = navigateBack)
        },
        content = {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.logInd2), style = Typography.h2)
            Spacer(modifier = Modifier.height(20.dp))
            textField(
                label = stringResource(R.string.email),
                text = email,
                onValueChange = { email = it })
            Spacer(modifier = Modifier.height(20.dp))
            textField(
                label = stringResource(R.string.password),
                text = password,
                hiddenText = true,
                onValueChange = { password = it })

            Spacer(modifier = Modifier.height(50.dp))
            LoginButton(
                onClick = { authViewModel?.login(email, password) },
                text = R.string.logInd,
                filled = true
            )
            Spacer(modifier = Modifier.height(80.dp))

            // changes for login
            loginFlow?.value?.let {
                when (it) {
                    is Resource.Failure -> {
//                    val context = LocalContext.current
                        Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Loading -> {
                        CircularProgressIndicator()
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navHostController?.navigate("Home")
                        }
                    }
                }
            }
        }
    }
    )
}

@Composable
fun textField(label: String, text: String, hiddenText: Boolean = false, onValueChange: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .border(1.dp, ButtonColorGreen, RoundedCornerShape(4.dp))

    ) {
        if(hiddenText)
            TextField(
                label = { Text(text = label) },
                value = text,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    cursorColor = MaterialTheme.colors.onBackground,
                    focusedIndicatorColor = ButtonColorGreen,
                    unfocusedIndicatorColor = MaterialTheme.colors.onPrimary
                ),
                modifier = Modifier.padding(5.dp),
                onValueChange = onValueChange)
        else
            TextField(
                label = { Text(text = label) },
                value = text,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                shape = RoundedCornerShape(4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    cursorColor = MaterialTheme.colors.onBackground,
                    focusedIndicatorColor = ButtonColorGreen,
                    unfocusedIndicatorColor = MaterialTheme.colors.onPrimary
                ),
                modifier = Modifier.padding(5.dp),
                onValueChange = onValueChange)
    }
}
