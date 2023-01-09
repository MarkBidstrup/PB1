package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.AuthViewModel
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.data.userData.UserDataRepository
import com.example.pb1_probe_application.dataClasses.*
import com.example.pb1_probe_application.ui.theme.StrokeColor
import com.google.firebase.firestore.auth.User

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeltagerListeScreen(id: String?, trialsViewModel: TrialsViewModel,navHostController: NavController, userViewModel: UserViewModel) {
    val currentTrialID = trialsViewModel.currentNavTrial?.trialID
    val registeredParticipants =
        currentTrialID?.let { trialsViewModel.getRegisteredParticipantsUIDList(it).collectAsState().value }
    val userDataList = registeredParticipants?.map {
        userViewModel.getViewModelUserData(it)
    }


    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = {
                      //TODO navigation

                    }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back",
                    )
                }
            }
        },
        content = {
            Spacer(modifier = Modifier.height(15.dp))
            Column(modifier = Modifier
                .padding(all = 8.dp)
                .padding(
                    bottom = 46.dp
                ))
            {
                LazyColumn (
                    contentPadding = PaddingValues(start = 9.dp, end = 9.dp),
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .weight(4f)
                ) {
                    /*items(userDataList) { //TODO commented out to suppress error
                        CardItem(userData = it)
                        Spacer(modifier = Modifier.height(15.dp))
                    }*/
                }
             }
        }
    )
}

@Composable
fun ContactInfo(
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
//    ("BJUd41JgLShgB5NvBTr1nNHkipk1")
    val user = userViewModel.userDataFlow.collectAsState().value
    Column(
        modifier = modifier.padding(
            start = 16.dp,
            top = 0.dp,
            bottom = 0.dp,
            end = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.Navn)+" "+ user?.name,
            style = MaterialTheme.typography.body2,
            modifier = modifier.padding(bottom = 8.dp),
        )
        Text(
            text = stringResource(R.string.EfterNavn)+" "+ user?.lastName,
            style = MaterialTheme.typography.body2,
            modifier = modifier.padding(bottom = 8.dp)
        )

        Text(
                text = stringResource(R.string.Email)+" "+ user?.email,
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
        )
        Text(
                text = stringResource(R.string.Telefonnummer)+" "+ user?.phone,
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
        )
    }
}

@Composable
fun CardItem(userData: UserData, modifier: Modifier = Modifier ){
    val trial = Trial()
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.border(1.dp, StrokeColor, RoundedCornerShape(10.dp))
    )
    {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                TrialTitle(trial.title)
                Spacer(Modifier.weight(1f))
            }
            //ContactInfo(userData) //TODO commented out to suppress error
            Spacer(Modifier.weight(1f))
            Divider(
                thickness = 1.dp,
                color = androidx.compose.ui.graphics.Color.LightGray,
                modifier = Modifier.padding(start = 17.dp, end = 17.dp)
            )
        }
        }
}