package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.data.auth.Resource
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ForgottenPasswordScreen(navHostController: NavHostController?, authViewModel: AuthViewModel?, navigateBack: () -> Unit) {

    // changes for login
    val resetFlow = authViewModel?.resetPasswordFlow?.collectAsState()

    var email by remember { mutableStateOf(authViewModel?.forgottenEmail) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            ProbeTopBar(icon = TopBarIcons.Clear, onClick = navigateBack)
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(100.dp))
                Text(text = stringResource(R.string.glemtKodeord), style = Typography.h2)
                Spacer(modifier = Modifier.height(20.dp))
                textField(
                    label = stringResource(R.string.email),
                    text = email!!,
                    onValueChange = { email = it },
                    focusManager = LocalFocusManager.current
                )

                Spacer(modifier = Modifier.height(50.dp))
                LoginButton(
                    onClick = { authViewModel?.resetPassword(email!!) },
                    text = R.string.nulstilKodeord,
                    filled = true
                )
                Spacer(modifier = Modifier.height(80.dp))

                resetFlow?.value?.let {
                    when (it) {
                        is Resource.Failure -> {
                            Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                        }
                        Resource.Loading -> {
                            CircularProgressIndicator()
                        }
                        is Resource.Success -> {
                            LaunchedEffect(Unit) {
                                Toast.makeText(context, R.string.tjekEmail, Toast.LENGTH_LONG).show()
                                navigateBack()
                            }
                        }
                    }
                }
            }
        }
    )
}