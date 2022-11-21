package com.example.pb1_probe_application.ui

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pb1_probe_application.data.auth.AuthViewModel
import com.example.pb1_probe_application.data.auth.Resource
import com.example.pb1_probe_application.ui.theme.ButtonColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun LogIn(navHostController: NavHostController?, authViewModel: AuthViewModel?){ //I think we need a navigation input/reference

    // changes for login
    val loginFlow = authViewModel?.loginFlow?.collectAsState()
    // changes for register
    val signupFlow = authViewModel?.signupFlow?.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()){

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Log Ind")

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "E-mail") },
                value = email,
//                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { email = it })
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Adgangskode") },

                value = password,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password = it })

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        // changes for login
                        authViewModel?.login(email,password)
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColorGreen)
                ) {
                    Text(text = "Login")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        // changes for register
                          signup(authViewModel = authViewModel, email = email, password = password, context)
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColorGreen)
                ) {
                    Text(text = "Register")
                }
            }
        }
        // changes for login
        loginFlow?.value?.let {
            when(it) {
                is Resource.Failure -> {
//                    val context = LocalContext.current
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                }
                Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is Resource.Success -> {
                    LaunchedEffect(Unit) {
                        navHostController?.navigate("HomeLoggedIn")
                    }
                }
            }
        }
        // changes for register
        signupFlow?.value?.let {
            when(it) {
                is Resource.Failure -> {
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                }
                Resource.Loading -> {
                }
                is Resource.Success -> {
                }
            }
        }
    }
}

private fun signup(authViewModel: AuthViewModel?, email: String, password: String, context: Context) {
    authViewModel?.signup(email,password)
    if (!(email.equals("") && password.equals("")))
        Toast.makeText(context,"Registration completed",Toast.LENGTH_LONG).show()
}

//@Preview
//@Composable
//private fun LogInPreview() {
//    LogIn(navHostController= navHostController?)
//
//}
