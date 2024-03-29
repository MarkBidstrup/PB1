package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pb1_probe_application.R
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.dataClasses.Role
import com.example.pb1_probe_application.dataClasses.Trial
import com.example.pb1_probe_application.navigation.BottomBar
import com.example.pb1_probe_application.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class TrialPostIcons {
     NotificationOn, NotificationOff, Contact, None
 }

enum class TopBarIcons {
    Search, Clear, None
}

 @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
 @Composable
fun TrialListingsScreen(trialsViewModel: TrialsViewModel = viewModel(), userViewModel: UserViewModel, navHostController: NavHostController?, loggedIn: Boolean, role: Role = Role.TRIAL_PARTICIPANT) {
     var trials = trialsViewModel.trials.collectAsState(emptyList()).value
     var myTrials: List<Trial> = ArrayList()
     var subscribedTrials: List<Trial> = ArrayList()
     var searchBoxExpanded by remember { mutableStateOf(false) }
     var displaySearchResults by remember { mutableStateOf(false) }
     val focusRequester = remember { FocusRequester()  }
     val focusManager = LocalFocusManager.current
     val scope = rememberCoroutineScope()
     val context = LocalContext.current

     if(loggedIn && role == Role.TRIAL_PARTICIPANT) {
         trialsViewModel.getViewModelSubscribedTrials()
         trialsViewModel.getViewModelMyTrialsParticipants()
         subscribedTrials = trialsViewModel.subscribedTrials.collectAsState().value
         myTrials = trialsViewModel.myTrialsParticipants.collectAsState().value
     }

    Scaffold(
        topBar = {
            if(!searchBoxExpanded)
                ProbeTopBar(icon = TopBarIcons.Search, onClick = {
                    // below lines of code is inspired by https://stackoverflow.com/questions/70654829/enable-and-focus-textfield-at-once-in-jetpack-compose
                    // reply by Kamil Maciuszek, Jan 10 2022
                    scope.launch {
                        searchBoxExpanded = true
                        delay(100)
                        focusRequester.requestFocus()
                    }
                })
            else {
                var searchWord by remember { mutableStateOf("") }
                SearchTopBar(searchWord,
                    focusRequester,
                    {searchWord = it},
                    searchOnClick = {
                        searchBoxExpanded = false
                        if(searchWord != "") {
                            trialsViewModel.getFilteredTrials(searchWord)
                            trialsViewModel.showFilterResult = false
                            displaySearchResults = true
                        } },
                    cancelOnClick = {searchBoxExpanded = false}
                )

            }
        },
        content = {
            Column(modifier = Modifier
                .padding(all = 8.dp)
                .padding(bottom = 46.dp)
                // next few lines of code clear the focus and keyboard when user taps outside of the keyboard
                // code is from https://stackoverflow.com/questions/69139853/android-compose-textfield-how-to-dismiss-keyboard-on-touch-outside
                // reply by Phil Dukhov, Sep 11 2021
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (navHostController != null) {
                        if(trialsViewModel.showFilterResult || displaySearchResults) {
                            trials = trialsViewModel.filteredTrials.collectAsState().value
                            if (trials.size != 1)
                                Text(
                                    modifier = Modifier.padding(top = 12.dp, start = 9.dp, bottom = 12.dp),
                                    text = stringResource(R.string.filtreredeStudier, trials.size), style = Typography.h2
                                )
                            else
                                Text(
                                    modifier = Modifier.padding(top = 12.dp, start = 9.dp, bottom = 12.dp),
                                    text = stringResource(R.string.filtreredeStudierSingular, trials.size), style = Typography.h2
                                )
                            Spacer(Modifier.weight(2f))
                            UnSearchFilterButton(displaySearchResults, onClick = {
                                trialsViewModel.showFilterResult = false
                                navHostController.navigate("Home") {
                                }
                            })
                        } else {
                            Text(
                                modifier = Modifier.padding(top = 12.dp, start = 9.dp, bottom = 12.dp),
                                text = stringResource(R.string.nyesteStudier), style = Typography.h2
                            )
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    FilterButton(onClick = {
                        navHostController?.navigate("Filter") {
                            launchSingleTop = true
                        }
                    })
                }

                LazyColumn(
                    contentPadding = PaddingValues(start = 9.dp, end = 9.dp),
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .weight(4f)) {
                    if(loggedIn && userViewModel.getUserRole() == null) {
                        scope.launch {
                            delay(20) // this is to make sure that role has had time to be updated
                        }
                    }
                    items(trials) {
                        var icon by remember { mutableStateOf(TrialPostIcons.None) }
                        var onClick: () -> Unit = {}
                        if(loggedIn && userViewModel.getUserRole() == Role.TRIAL_PARTICIPANT) { // subscribe button is only shown for logged in trial participants
                            trialsViewModel.getViewModelSubscribedTrials()
                            trialsViewModel.getViewModelMyTrialsParticipants()
                            subscribedTrials = trialsViewModel.subscribedTrials.collectAsState().value
                            myTrials = trialsViewModel.myTrialsParticipants.collectAsState().value
                            if(subscribedTrials.contains(it)) {
                                icon = TrialPostIcons.NotificationOff
                                onClick = { icon = TrialPostIcons.NotificationOn
                                    Toast.makeText(context, R.string.removedSubscribedTrials, Toast.LENGTH_LONG).show()
                                    trialsViewModel.unsubscribeFromTrial(it) }
                            }
                            else {
                                icon = TrialPostIcons.NotificationOn
                                onClick = { icon = TrialPostIcons.NotificationOff
                                    Toast.makeText(context, R.string.addedSubscribedTrials, Toast.LENGTH_LONG).show()
                                    trialsViewModel.subscribeToTrial(it)  }
                            }
                        }
                        val applyButtonEnabled: Boolean =
                            if (!loggedIn)
                                true
                            else
                                userViewModel.getUserRole() == Role.TRIAL_PARTICIPANT && !myTrials.contains(it)
                        val applyOnClick: () -> Unit =
                            if(loggedIn)
                            { {
                                trialsViewModel.setCurrentNavTrialID(it)
                                navHostController?.navigate("DeltagerInfo") {
                                    launchSingleTop = true
                                } } }
                            else { {
                                navHostController?.navigate("NotLoggedIn") {
                                    launchSingleTop = true
                                }
                            }}
                        TrialItem(trial = it, iconUsed = icon, applyOnClick = applyOnClick,
                            buttonEnabled = applyButtonEnabled, iconOnClick = onClick,
                         readMoreOnClick = {
                             trialsViewModel.setCurrentNavTrialID(it)
                             navHostController?.navigate("ReadMoreTrialPost") {
                                 launchSingleTop = true
                             }
                         })
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
                            navHostController?.navigate("logInd") {
                                launchSingleTop = true
                            }
                        }, R.string.logInd, false)
                        LoginButton(onClick = {
                            navHostController?.navigate("register") {
                                launchSingleTop = true
                            }
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
              iconOnClick: () -> Unit, applyOnClick: () -> Unit, readMoreOnClick: () -> Unit) {
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
                    modifier = modifier
                        .padding(start = 8.dp, top = 16.dp)
                        .clickable(onClick = readMoreOnClick),)
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
                .testTag(
                    if (expanded) Icons.Filled.ExpandLess.toString()
                    else Icons.Filled.ExpandMore.toString()
                )
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
            val locations = stringResource(R.string.lokation) +" " + trial.locations
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
                .padding(top = 10.dp)
//                .rotate(rotateAngle)
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
fun SearchTopBar(input: String, focusRequester: FocusRequester, onValueChange: (String) -> Unit, searchOnClick: () -> Unit, cancelOnClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier
            .padding(start = 17.dp, top = 17.dp, bottom = 17.dp)
            .weight(1f)
            .border(0.5.dp, Color.Gray, MaterialTheme.shapes.small)
        ) {
            BasicTextField(
                value = input,
                singleLine = true,
                modifier = Modifier
                    .padding(start = 10.dp, top = 4.dp, end = 3.dp)
                    .fillMaxSize()
                    .focusRequester(focusRequester),
                onValueChange = onValueChange,
                textStyle = Typography.body1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { searchOnClick()
                    }
                )
            )
        }
        IconButton(onClick = cancelOnClick, modifier = modifier
            .padding(end = 2.dp)) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "search",
                modifier = modifier.scale(1.2f)
            )
        }
        IconButton(onClick = searchOnClick, modifier = modifier
            .padding(end = 10.dp)) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "search",
                modifier = modifier.scale(1.2f)
            )
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
                .scale(1.5f)
        )
        Text(
            text = stringResource(R.string.filtrer),
            style = MaterialTheme.typography.body2,)
    }
}

@Composable
fun UnSearchFilterButton(
    search: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onPrimary),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        val iconUsed = if (search)
            Icons.Filled.SearchOff
        else
            Icons.Filled.FilterAltOff
        val text = if (search)
            R.string.unsearch
        else
            R.string.unfiltrer
        Icon(
            imageVector = iconUsed,
            contentDescription = stringResource(R.string.unfiltrer_forklaring),
            modifier = modifier
                .height(16.dp)
                .padding(end = 2.dp)
                .scale(1.5f)
        )
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.body2)
    }
}

/**
 * Composable that displays what the UI of the app looks like in light theme in the design tab.
 */
@Preview
@Composable
fun TrialPreview() {
    PB1ProbeApplicationTheme(darkTheme = false) {
//        TrialListingsScreen(navHostController = null, loggedIn = false)
    }
}
