package com.example.pb1_probe_application.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Trial
import com.example.pb1_probe_application.data.trials
import com.example.pb1_probe_application.model.Role
import com.example.pb1_probe_application.model.TrialState
import com.example.pb1_probe_application.model.TrialsViewModel
import com.example.pb1_probe_application.ui.theme.ButtonColorGreen
import com.example.pb1_probe_application.ui.theme.PB1ProbeApplicationTheme
import com.example.pb1_probe_application.ui.theme.MediumGrey
import com.example.pb1_probe_application.ui.theme.Typography
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

enum class TabPage() {
    APPLIED, FOLLOWING
}

@Composable
fun MyTrials(trialsViewModel: TrialsViewModel = viewModel(), navHostController: NavHostController = rememberNavController(), role: Role = Role.TRIAL_PARTICIPANT) {
    val trials by trialsViewModel.uiState.collectAsState()
// TODO - check when/ how often things recompose when using kotlin flows
// TODO - check if a different state needs to be passed in depending on whether it's a researcher or patient
    TrialsList(trials, Modifier, navHostController, role)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TrialsList(trialList: List<TrialState>, modifier: Modifier = Modifier, navHostController: NavHostController, role: Role = Role.TRIAL_PARTICIPANT) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.mineStudier), style = Typography.h1)},
                backgroundColor = MaterialTheme.colors.onPrimary,
                elevation = 0.dp
            )
        },
        content = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp)) {
                if(role == Role.RESEARCHER) {
                    Text(
                        modifier = Modifier.padding(start = 17.dp, bottom = 12.dp),
                        text = stringResource(R.string.aktiveStudier), style = Typography.h2
                    )
                    if (trialList.isEmpty()) {
                        Spacer(modifier = Modifier.height(250.dp))
                        Text(
                            modifier = Modifier.align(CenterHorizontally),
                            text = stringResource(R.string.ingenAktiveStudier), style = Typography.body1
                        )
                    } else {
                        LazyColumn(
                            modifier = modifier.weight(4f),
                            contentPadding = PaddingValues(start = 17.dp, end = 17.dp)
                        ) {
                            items(trialList) {
                                ResearcherTrialPost(trialInfo = it)
                                if (!(trialList.indexOf(it) == trialList.lastIndex))
                                    Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
                        Spacer(modifier = Modifier.weight(.2f))
                        PostNewTrialButton(
                            Modifier
                                .align(CenterHorizontally)
                                .height(45.dp)
                                .padding(bottom = 5.dp)
                        )
                    }
                } else { // trial participant
                    val pagerState = rememberPagerState(pageCount = TabPage.values().size)
                    val scope = rememberCoroutineScope()
                    TrialParticipantTabs(
                        selectedTabIndex = pagerState.currentPage,
                        onSelectedTab = {
                            scope.launch {
                                pagerState.animateScrollToPage(it.ordinal)
                            }
                        },
                        pagerState
                    )
                    HorizontalPager(state = pagerState, modifier = Modifier.weight(1f))
                    { index ->
                        if (trialList.isEmpty()) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                val str =
                                    if (TabPage.values()[index]  == TabPage.FOLLOWING)
                                        stringResource(R.string.ingenFulgteStudier)
                                    else
                                        stringResource(R.string.ingenAktiveStudier)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    modifier = modifier.align(CenterHorizontally).weight(1.2f),
                                    text = str, style = Typography.body1
                                )
                            }
                        } else {
                            LazyColumn(contentPadding = PaddingValues(start = 17.dp, end = 17.dp)
                            ) {
                                items(trials) {
                                    ParticipantTrialPost(trial = it, selectedTabIndex = pagerState.currentPage)
                                    if (!(trials.indexOf(element = it) == trials.lastIndex))
                                        Spacer(modifier = Modifier.height(15.dp))
                                }
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            BottomBar(navController = navHostController)
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TrialParticipantTabs(selectedTabIndex: Int, onSelectedTab: (TabPage) -> Unit, pagerState: PagerState) {
    TabRow(selectedTabIndex = selectedTabIndex,
        backgroundColor = MaterialTheme.colors.onPrimary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        modifier = Modifier.padding(start = 17.dp, end = 17.dp, bottom = 12.dp)
    ) {
        TabPage.values().forEachIndexed { index, tab ->
            Tab(selected = selectedTabIndex == index,
                onClick = { onSelectedTab(tab) },
                text = {
                    val str =
                        if(tab == TabPage.APPLIED)
                            stringResource(R.string.tilmeldteStudier)
                        else
                            stringResource(R.string.fulgteStudier)
                    Text(str,style = Typography.h2)},
                unselectedContentColor = MediumGrey,
            )
        }
    }
}

@Composable
private fun PostNewTrialButton(modifier: Modifier) {
    Button(
        modifier = modifier,
        onClick = { /*TODO*/  },
        shape = RoundedCornerShape(6.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 10.dp ),
        colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColorGreen)
    ) {
        Row( ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "add",
                Modifier,
                Color.Black
            )
            Spacer(modifier = Modifier.padding(1.dp))
            Text(stringResource(R.string.opretStudie), style = Typography.body1,
                fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun ParticipantTrialPost(trial: Trial, selectedTabIndex: Int) { //TODO - call the function with different parameters depending on selectedTabIndex
    if(selectedTabIndex == 0)
        TrialItem(trial = trial, iconUsed = TrialPostIcons.Contact)
    else
        TrialItem(trial = trial, iconUsed = TrialPostIcons.NotificationOff)
}

@Composable
fun ResearcherTrialPost(trialInfo: TrialState) {
    val shape = RoundedCornerShape(10.dp)
    Card(
        elevation = 4.dp,
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, shape)
    ) {
        Row (modifier = Modifier.padding(top = 5.dp, end= 5.dp, bottom = 5.dp, start = 10.dp),
            Arrangement.SpaceEvenly){
            Column(modifier = Modifier
                .height(110.dp)
                .weight(3f),
                verticalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = trialInfo.trialName,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = stringResource(R.string.tilmeldingsfrist) + " "+ trialInfo.registrationDeadline,
                    style = MaterialTheme.typography.body2)
                Text(
                    text = stringResource(R.string.antalTilmeldte) + " "+ trialInfo.numParticipantsRegistered,
                    style = MaterialTheme.typography.body2)
                Text(
                    text = stringResource(R.string.potentielleKandidater) + " "+ trialInfo.numPotentialParticipants,
                    style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(1.dp))
            }
            Spacer(modifier = Modifier.weight(.1f))
            Column(modifier = Modifier
                .height(110.dp)
                .width(130.dp)
                .padding(top = 8.dp, bottom = 8.dp, end = 3.dp)) {
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth()
                        .height(35.dp),
                    onClick = { /*TODO*/  },
                    shape = RoundedCornerShape(6.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 10.dp ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColorGreen)
                ) {
                    Text(stringResource(R.string.haandterStudie), style = Typography.button,
                        fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth()
                        .height(35.dp),
                    onClick = { /*TODO*/  },
                    shape = RoundedCornerShape(6.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 10.dp ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColorGreen)
                ) {
                    Text(stringResource(R.string.anmod), style = Typography.button,
                        fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ResearcherTrialsScreenPreview() {
    PB1ProbeApplicationTheme(darkTheme = false) {
        MyTrials(role = Role.RESEARCHER)
    }
}