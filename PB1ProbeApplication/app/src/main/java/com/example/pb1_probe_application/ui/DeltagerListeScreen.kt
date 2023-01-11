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
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.dataClasses.*
import com.example.pb1_probe_application.ui.theme.StrokeColor
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeltagerListeScreen( trialsViewModel: TrialsViewModel,navHostController: NavController, userViewModel: UserViewModel,onNavBack: () -> Unit) {
    val currentTrialID = trialsViewModel.currentNavTrial?.trialID
    val registeredParticipants =
        currentTrialID?.let { trialsViewModel.getRegisteredParticipantsUIDList(it).collectAsState().value }
    val userDataList = mutableListOf<UserData>()
    if (registeredParticipants != null) {
        for (uid in registeredParticipants) {
            userViewModel.getViewModelUserData(uid)
            val user = userViewModel.userDataFlow.collectAsState().value
            if (user != null) {userDataList.add(user)}
        }
    }
    Scaffold(
        topBar = {

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(
                    onClick = {
                       onNavBack()
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
                    items(userDataList) {
                        CardItem(userData = it, trial = trialsViewModel.currentNavTrial!!)
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
             }
        }
    )
}
@Composable
fun ContactInfo(
    userData: UserData,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(
            start = 16.dp,
            top = 0.dp,
            bottom = 0.dp,
            end = 16.dp
        )
    ) {
            Text(
                text = stringResource(R.string.Navn)+" "+ userData.name + " " + userData.lastName,
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
            )


        Text(
                text = stringResource(R.string.Email)+" "+ userData.email,
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
        )
        Text(
                text = stringResource(R.string.Telefonnummer)+" "+ userData.phone,
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
        )
    }
}
@Composable
fun CardItem(userData: UserData, modifier: Modifier = Modifier,trial: Trial){
    TrialTitle(trial.title)

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
                Spacer(Modifier.weight(1f))
            }
            ContactInfo(userData)
        }
        }
}
