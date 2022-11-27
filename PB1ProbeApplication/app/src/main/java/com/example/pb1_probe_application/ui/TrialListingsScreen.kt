package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pb1_probe_application.R
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.model.Role
import com.example.pb1_probe_application.model.Trial
import com.example.pb1_probe_application.navigation.BottomBar
import com.example.pb1_probe_application.ui.theme.*

 enum class TrialPostIcons {
     NotificationOn, NotificationOff, Contact, None
 }

enum class TopBarIcons {
    Search, Clear, None
}

 @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
 @Composable
fun TrialListingsScreen(trialsViewModel: TrialsViewModel = viewModel(), navHostController: NavHostController?, loggedIn: Boolean, role: Role = Role.TRIAL_PARTICIPANT) {
     val trials = trialsViewModel.trials.collectAsState(emptyList()).value
     var myTrials: List<Trial> = ArrayList()
     var subscribedTrials: List<Trial> = ArrayList()
     if(loggedIn && role == Role.TRIAL_PARTICIPANT) {
         trialsViewModel.getViewModelSubscribedTrials()
         subscribedTrials = trialsViewModel.subscribedTrials.collectAsState().value
         trialsViewModel.getViewModelMyTrialsParticipants()
         myTrials = trialsViewModel.myTrialsParticipants.collectAsState().value
     }

    Scaffold(
        topBar = {
            ProbeTopBar(icon = TopBarIcons.Search, onClick = {}) //TODO - implement search onClick
        },
        content = {
            Column(modifier = Modifier
                .padding(all = 8.dp)
                .padding(bottom = 46.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(top = 12.dp, start = 17.dp, bottom = 12.dp),
                        text = stringResource(R.string.nyesteStudier), style = Typography.h2
                    )
                    Spacer(Modifier.weight(1f))
                    FilterButton(onClick = {
                        //TODO: implement onClick
                        navHostController?.navigate("Filter")
                    })
                }

                LazyColumn(
                    contentPadding = PaddingValues(start = 9.dp, end = 9.dp),
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .weight(4f)) {

                    items(trials) {
                        var icon by remember { mutableStateOf(TrialPostIcons.None) }
                        var onClick: () -> Unit = {}
                        if(loggedIn && role == Role.TRIAL_PARTICIPANT) { // subscribe button is only shown for logged in trial participants
                            if(subscribedTrials.contains(it)) {
                                icon = TrialPostIcons.NotificationOff
                                onClick = { icon = TrialPostIcons.NotificationOn
                                            trialsViewModel.unsubscribeFromTrial(it) }
                            }
                            else {
                                icon = TrialPostIcons.NotificationOn
                                onClick = { icon = TrialPostIcons.NotificationOff
                                            trialsViewModel.subscribeToTrial(it)  }
                            }
                        }
                        val applyButtonEnabled: Boolean =
                            if (!loggedIn)
                                true
                            else
                                role == Role.TRIAL_PARTICIPANT && !myTrials.contains(it)
                        val applyOnClick: () -> Unit =
                            if(loggedIn)
                            { {  navHostController?.navigate("DeltagerInfo/{trialID}") } }
                                    // TODO navigate with trialID argument
                            else { {
                                navHostController?.navigate("NotLoggedIn")
                            }}
                        TrialItem(trial = it, iconUsed = icon, applyOnClick = applyOnClick,
                            buttonEnabled = applyButtonEnabled, iconOnClick = onClick)
                        if (trials.indexOf(it) != trials.size)
                            Spacer(modifier = Modifier.height(15.dp))
                    }
                }
                Spacer(modifier = Modifier.weight(.2f))
                if (!loggedIn) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .align(alignment = CenterHorizontally)
                    ) {
                        LoginButton(onClick = {
                            navHostController?.navigate("logInd")
                        }, R.string.logInd, false)
                        LoginButton(onClick = {
                            navHostController?.navigate("register")
                        }, R.string.registrer, true)
                    }
                }
            }
        },
        bottomBar = {
            if (navHostController != null)
                BottomBar(navController = navHostController)
        }

    )

}


@Composable
fun TrialItem(trial: Trial, modifier: Modifier = Modifier, iconUsed: TrialPostIcons, buttonEnabled: Boolean,
              iconOnClick: () -> Unit, applyOnClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .border(1.dp, StrokeColor, RoundedCornerShape(10.dp))

    ) {
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
                if(iconUsed != TrialPostIcons.None) {
                    NotificationButton(add = iconUsed, onClick = iconOnClick)
                    Spacer(Modifier.weight(1f))
                }
                TrialExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded })
            }
            TrialInfo(trial, expanded)
            if (expanded) Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.mereInfo),
                    style = MaterialTheme.typography.body2,
                    color = ReadMoreColor,
                    modifier = modifier.padding(start = 8.dp, top = 16.dp),)
                Spacer(Modifier.weight(1f))
                TrialApplyButton(buttonEnabled, onClick = applyOnClick)
            }
        }
    }
}

@Composable
fun TrialExpandButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            //tint = MaterialTheme.colors.secondary,
            contentDescription = stringResource(R.string.expand_forklaring),
            modifier = modifier
                .scale(2f)
                .padding(end = 2.dp)
        )
    }
}

@Composable
fun NotificationButton(
    add: TrialPostIcons,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = when (add) {
                TrialPostIcons.NotificationOn -> Icons.Filled.NotificationAdd
                TrialPostIcons.NotificationOff -> Icons.Filled.NotificationsOff
                else -> {Icons.Filled.Message}
            },
            //tint = MaterialTheme.colors.secondary,
            contentDescription = when (add) {
                TrialPostIcons.NotificationOn -> "notificationsOn"
                TrialPostIcons.NotificationOff -> "notificationsOff"
                else -> "messageContact"
            },
            modifier = modifier
                .scale(1f)
        )
    }
}

@Composable
fun TrialTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.h3,
        modifier = modifier
            .padding(top = 4.dp, start = 4.dp)
            .width(245.dp)
    )
}

@Composable
fun TrialInfo(
    trial: Trial,
    expanded: Boolean,
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
            text = stringResource(R.string.formaal) +" "+ trial.purpose,
            style = MaterialTheme.typography.body2,
            modifier = modifier.padding(bottom = 8.dp),
        )
        Text(
            text = stringResource(R.string.tilmeldingsfrist) +" "+ trial.registrationDeadline,
            style = MaterialTheme.typography.body2,
            modifier = if (expanded) modifier.padding(bottom = 8.dp) else modifier.padding(bottom = 16.dp)
        )
        if (expanded) {
            var locations = stringResource(R.string.lokation) +" "
            if (trial.locations.isNotEmpty()) {
                locations += trial.locations[0].hospitalName
                for (i in 2 .. trial.locations.size)
                    locations += ", " + trial.locations[i-1].hospitalName
            }
            Text(
                text = locations,
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(R.string.forloeb) +" "+ trial.startDate + "-" + trial.endDate,
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(R.string.varighed) +" "+ trial.trialDuration,
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
            )
            var diagnoses = stringResource(R.string.sygdom) +" "
            if(trial.diagnoses.isNotEmpty()) {
                diagnoses += trial.diagnoses[0]
                for (i in 2 .. trial.diagnoses.size)
                    diagnoses += ", " + trial.diagnoses[i-1]
            }
            Text(
                text = diagnoses,
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(R.string.intervention) +" "+ trial.interventions,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@Composable
fun TrialApplyButton(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .width(95.dp)
            .height(40.dp)
            .padding(bottom = 4.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 10.dp ),
        colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColorGreen, disabledBackgroundColor = LockedApplyButtonColor)
    ) {
        Text(stringResource(R.string.ansoeg), style = Typography.body1,//use body1 for slightly larger fontsize
            fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

@Composable
fun LoginButton(
    onClick: () -> Unit,
    text: Int,
    filled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = Modifier
            .width(184.dp)
            .height(58.dp)
            .padding(start = 6.dp, end = 6.dp)
            .border(2.dp, ButtonColorGreen, RoundedCornerShape(6.dp)),
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 10.dp ),
        colors = ButtonDefaults.buttonColors(backgroundColor = if (filled) ButtonColorGreen else Color.White)
    ) {
        Text(stringResource(text), style = Typography.button,
            fontWeight = FontWeight.Bold, color = Color.Black)
    }
}

/**
 * Composable that displays a Top Bar with an icon and text.
 *
 * @param modifier modifiers to set to this composable
 */
@Composable
fun ProbeTopBar(icon: TopBarIcons, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.final_icon),
            contentDescription = stringResource(R.string.logo),
            modifier = modifier
                .align(Center)
                .wrapContentWidth(CenterHorizontally)
                .padding(top = 10.dp),
        )
        if (icon != TopBarIcons.None) {
            IconButton(onClick = onClick, modifier = modifier
                .padding(end = 10.dp)
                .align(Alignment.BottomEnd)) {
                Icon(
                    imageVector = when (icon) {
                        TopBarIcons.Clear -> Icons.Filled.Clear
                        else -> {Icons.Filled.Search}
                    },
                    contentDescription = when (icon) {
                        TopBarIcons.Clear -> "clear"
                        else -> "search"
                    },
                    modifier = modifier
                        .scale(1.2f)
                )
            }
        }
    }

}

@Composable
fun FilterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary),
        elevation = ButtonDefaults.elevation(0.dp)
        ) {
        Icon(
            imageVector = Icons.Filled.FilterAlt,
            contentDescription = stringResource(R.string.filtrer_forklaring),
            modifier = modifier
                .height(16.dp)
                .padding(end = 2.dp)
        )
        Text(
            text = stringResource(R.string.filtrer),
            style = MaterialTheme.typography.body2,)
    }
}

/**
 * Composable that displays what the UI of the app looks like in light theme in the design tab.
 */
@Preview
@Composable
fun TrialPreview() {
    PB1ProbeApplicationTheme(darkTheme = false) {
        TrialListingsScreen(navHostController = null, loggedIn = false)
    }
}
