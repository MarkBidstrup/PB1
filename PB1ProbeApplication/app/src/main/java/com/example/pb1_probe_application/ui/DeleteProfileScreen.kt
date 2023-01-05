package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeleteProfileScreen(onClick: () -> Unit, logOutNav :() -> Unit, trialsViewModel: TrialsViewModel, authViewModel: AuthViewModel?, userViewModel: UserViewModel) {
    val uid = authViewModel!!.currentUser!!.uid
    userViewModel.setCurrentUser(uid)

    Scaffold (
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(id = R.string.sletProfil), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {
                    onClick()
                }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back arrow"
                    )
                }
            }
        },
        content = {
            Column(modifier = Modifier
                .padding(17.dp)
                .padding(bottom = 46.dp)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally){
                Spacer(modifier = Modifier.weight(1f))
                Text(text = stringResource(R.string.ErDuSikkerSlet), style = Typography.h2,
                    textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.weight(1.5f))
                Row(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                ) {
                    LoginButton(onClick = {
                        authViewModel.delete()
                        trialsViewModel.deleteCurrentUserFromAllTrialDBEntries()
                        userViewModel.deleteUser(uid)
                        logOutNav()
                    }, R.string.SLETPROFILCAPS, true)
                    LoginButton(onClick = {
                        onClick()
                    }, R.string.annuller, false)
                }
            }
        }
    )
}