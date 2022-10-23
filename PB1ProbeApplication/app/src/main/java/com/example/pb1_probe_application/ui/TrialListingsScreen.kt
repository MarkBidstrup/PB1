package com.example.pb1_probe_application.ui

import androidx.annotation.StringRes
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Trial
import com.example.pb1_probe_application.data.trials
import com.example.pb1_probe_application.ui.theme.PB1ProbeApplicationTheme
import com.example.pb1_probe_application.ui.theme.StrokeColor


@Composable
fun TrialListingsScreen() {
    Scaffold(
        topBar = {
            ProbeTopBar()
        }
    ) {
        LazyColumn(modifier = Modifier.background(MaterialTheme.colors.background)) {
            items(trials) {
                TrialItem(trial = it)
            }
        }
    }
}

@Composable
fun TrialItem(trial: Trial, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.padding(8.dp).
        border(2.dp, Color.LightGray, RoundedCornerShape(10.dp)
    )
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
                TrialExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded })
            }
            TrialInfo(trial, expanded)
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
            modifier = modifier.scale(2f).padding(end = 2.dp)
        )
    }
}


@Composable
fun TrialTitle(@StringRes title: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(title),
        style = MaterialTheme.typography.h3,
        modifier = modifier.padding(top = 4.dp, start = 4.dp).width(300.dp)
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
            bottom = 16.dp,
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
            modifier = modifier.padding(bottom = 8.dp),
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
                modifier = modifier.padding(bottom = 8.dp),
            )
        }
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
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }
}

/**
 * Composable that displays what the UI of the app looks like in light theme in the design tab.
 */
@Preview
@Composable
fun TrialPreview() {
    PB1ProbeApplicationTheme(darkTheme = false) {
        TrialListingsScreen()
    }
}

/**
 * Composable that displays what the UI of the app looks like in dark theme in the design tab.
 */
@Preview
@Composable
fun TrialDarkThemePreview() {
    PB1ProbeApplicationTheme(darkTheme = true) {
        TrialListingsScreen()
    }
}