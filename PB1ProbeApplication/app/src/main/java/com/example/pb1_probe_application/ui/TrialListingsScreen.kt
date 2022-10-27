 package com.example.pb1_probe_application.ui

import androidx.annotation.StringRes
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Trial
import com.example.pb1_probe_application.data.trials
import androidx.navigation.NavHostController
import com.example.pb1_probe_application.ui.theme.*

 enum class TrialPostIcons() {
     NotificationOn, NotificationOff, Contact
 }

 @Composable
fun TrialListingsScreen(navHostController: NavHostController?, loggedIn: Boolean) {

    Scaffold(
        topBar = {
            ProbeTopBar()
        },
        content = {
            Column(modifier = Modifier.padding(all = 8.dp).padding(bottom = 46.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(top = 12.dp, start = 17.dp, bottom = 12.dp),
                        text = stringResource(R.string.nyesteStudier), style = Typography.h2
                    )
                    Spacer(Modifier.weight(1f))
                    FilterButton(onClick = {})
                }

                LazyColumn(
                    contentPadding = PaddingValues(start = 9.dp, end = 9.dp),
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .weight(4f)) {
                    items(trials) {
                        TrialItem(trial = it, iconUsed = TrialPostIcons.NotificationOn)
                        if (trials.indexOf(it) != trials.lastIndex)
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
                        LoginButton(onClick = {}, R.string.registrer, true)
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
fun TrialItem(trial: Trial, modifier: Modifier = Modifier, iconUsed: TrialPostIcons) {
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
                TrialTitle(trial.titel)
                Spacer(Modifier.weight(1f))
                NotificationButton(add = iconUsed, onClick = {})
                Spacer(Modifier.weight(1f))
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
                TrialApplyButton(onClick = {})
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
fun TrialTitle(@StringRes title: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(title),
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
            text = stringResource(R.string.formaal) +" "+ stringResource(trial.formaal),
            style = MaterialTheme.typography.body2,
            modifier = modifier.padding(bottom = 8.dp),
        )
        Text(
            text = stringResource(R.string.tilmeldingsfrist) +" "+ stringResource(trial.tilmeldingsfrist),
            style = MaterialTheme.typography.body2,
            modifier = if (expanded) modifier.padding(bottom = 8.dp) else modifier.padding(bottom = 16.dp)
        )
        if (expanded) {
            Text(
                text = stringResource(R.string.lokation) +" "+ stringResource(trial.tilmeldingsfrist),
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(R.string.forloeb) +" "+ stringResource(trial.forloeb),
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(R.string.varighed) +" "+ stringResource(trial.varighed),
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(R.string.sygdom) +" "+ stringResource(trial.sygdom),
                style = MaterialTheme.typography.body2,
                modifier = modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(R.string.intervention) +" "+ stringResource(trial.intervention),
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@Composable
fun TrialApplyButton(
    onClick: () -> Unit,
) {
    Button(
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .width(95.dp)
            .height(40.dp)
            .padding(bottom = 4.dp),
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 10.dp ),
        colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColorGreen)
    ) {
        Text(stringResource(R.string.ansoeg), style = Typography.button,
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
fun ProbeTopBar(modifier: Modifier = Modifier) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Image(
            painter = painterResource(R.drawable.final_icon),
            contentDescription = stringResource(R.string.logo),
            modifier = modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
                .fillMaxWidth(),
        )
        /*Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.soegForklaring),
            modifier = modifier
                .height(16.dp)
                .wrapContentWidth((Alignment.End))
                .padding(8.dp)
        )*/
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
        TrialListingsScreen(null, false)
    }
}
