package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.auth.AuthViewModel
import com.example.pb1_probe_application.data.auth.Resource
import com.example.pb1_probe_application.ui.theme.ButtonColorGreen
import com.example.pb1_probe_application.ui.theme.StrokeColor
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LogInScreen(navHostController: NavHostController?, authViewModel: AuthViewModel?){ //I think we need a navigation input/reference

    // changes for login
    val loginFlow = authViewModel?.loginFlow?.collectAsState()

    var email by remember { mutableStateOf("test@test.com") }
    var password by remember { mutableStateOf("123456") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.final_icon),
                    contentDescription = stringResource(R.string.logo),
                    modifier = Modifier
                        .padding(top = 8.dp),
                )
                IconButton(onClick = {} ) { // TODO - navigate back
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "clear",
                        modifier = Modifier
                            .height(16.dp)
                            .padding(8.dp)
                    )
                }
            }
        },
        content = {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.logInd2), style = Typography.h2)
            Spacer(modifier = Modifier.height(20.dp))
            textField(label = stringResource(R.string.email), text = email, onValueChange = {email = it})
            Spacer(modifier = Modifier.height(20.dp))
            textField(label = stringResource(R.string.password), text = password, onValueChange = {password = it})

            Spacer(modifier = Modifier.height(50.dp))
            LoginButton(onClick = {  authViewModel?.login(email,password)}, text = R.string.logInd, filled = true)
        }
        // changes for login
        loginFlow?.value?.let {
            when(it) {
                is Resource.Failure -> {
//                    val context = LocalContext.current
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                }
                Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier)
                }
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navHostController?.navigate("Home")
                    }
                }
            }
        }
    }
    )
}

@Composable
fun textField(label: String, text: String, onValueChange: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .border(1.dp, ButtonColorGreen, RoundedCornerShape(4.dp))

    ) {
        TextField(
            label = { Text(text = label) },
            value = text,
//                visualTransformation = PasswordVisualTransformation(),
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

private fun signup(authViewModel: AuthViewModel?, email: String, password: String, context: Context) {
    authViewModel?.signup(email,password)
    if (!(email.equals("") && password.equals("")))
        Toast.makeText(context,"Registration completed",Toast.LENGTH_LONG).show()
}

//// changes for register
//signupFlow?.value?.let {
//    when(it) {
//        is Resource.Failure -> {
//            Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
//        }
//        Resource.Loading -> {
//        }
//        is Resource.Success -> {
//        }
//    }
//}

//@Preview
//@Composable
//private fun LogInPreview() {
//    LogIn(navHostController= navHostController?)
//
//}