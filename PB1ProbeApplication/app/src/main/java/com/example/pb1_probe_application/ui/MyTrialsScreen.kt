package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.application.TrialsViewModel
import com.example.pb1_probe_application.application.UserViewModel
import com.example.pb1_probe_application.dataClasses.Role
import com.example.pb1_probe_application.dataClasses.Trial
import com.example.pb1_probe_application.navigation.BottomBar
import com.example.pb1_probe_application.ui.theme.*
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

enum class TabPage {
    APPLIED, FOLLOWING
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyTrials(modifier: Modifier = Modifier, trialsViewModel: TrialsViewModel = viewModel(), navHostController: NavHostController = rememberNavController(), role: Role = Role.TRIAL_PARTICIPANT, userViewModel: UserViewModel) {
    val subscribedTrials: List<Trial>
    val myTrials: List<Trial>
    if (role == Role.TRIAL_PARTICIPANT) {
        trialsViewModel.getViewModelMyTrialsParticipants()
        trialsViewModel.getViewModelSubscribedTrials()
        myTrials = trialsViewModel.myTrialsParticipants.collectAsState().value
        subscribedTrials = trialsViewModel.subscribedTrials.collectAsState().value
    }
    else {
        trialsViewModel.getViewModelMyTrialsResearchers()
        myTrials = trialsViewModel.myTrialsResearcher.collectAsState().value
        subscribedTrials = ArrayList()
    }

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
                .padding(bottom = 70.dp)
            ) {
                if(role == Role.RESEARCHER) {
                    Text(
                        modifier = Modifier.padding(start = 17.dp, bottom = 12.dp),
                        text = stringResource(R.string.aktiveStudier), style = Typography.h2
                    )
                    if (myTrials.isEmpty()) {
                        Spacer(modifier = Modifier.height(200.dp))
                        Text(
                            text = stringResource(R.string.ingenAktiveStudier), style = Typography.body1,
                            modifier = Modifier.align(CenterHorizontally) )
                        Spacer(modifier = modifier.weight(1f))
                    } else {
                        LazyColumn(
                            modifier = modifier.weight(1f),
                            contentPadding = PaddingValues(start = 17.dp, end = 17.dp)
                        ) {
                            items(myTrials) {
                                val list = trialsViewModel.getRegisteredParticipantsUIDList(it.trialID).collectAsState().value
                                val potentialCandidates = trialsViewModel.getTotalNumOfPotentialCandidates(it.trialID, it.diagnoses).collectAsState().value
                                ResearcherTrialPost(it, list.size, potentialCandidates) {
                                    trialsViewModel.setCurrentNavTrialID(it)
                                    navHostController.navigate("ManageTrial") {
                                        launchSingleTop = true
                                    } }
                                if (myTrials.indexOf(it) != myTrials.lastIndex)
                                    Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    PostNewTrialButton(
                        modifier
                            .height(45.dp)
                            .padding(bottom = 5.dp)
                            .align(CenterHorizontally)
                    ) {
                        navHostController.navigate("CreateTrial")
                    }
                } else { // trial participant
                    val pagerState = rememberPagerState(pageCount = TabPage.values().size)
                    val scope = rememberCoroutineScope()
                    // source: the horizontal pager tabs were inspired by: https://www.youtube.com/watch?v=D6WNjEu6Y9c
                    TrialParticipantTabs(
                        selectedTabIndex = pagerState.currentPage,
                        onSelectedTab = {
                            scope.launch {
                                pagerState.animateScrollToPage(it.ordinal)
                            }
                        },
                        pagerState
                    )
                    HorizontalPager(state = pagerState, modifier = modifier)
                    { index ->
                        val trials1 =
                            if(pagerState.currentPage == 0)
                                myTrials
                            else
                                subscribedTrials
                        if (trials1.isEmpty()) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                val str =
                                    if (TabPage.values()[index]  == TabPage.FOLLOWING)
                                        stringResource(R.string.ingenFulgteStudier)
                                    else
                                        stringResource(R.string.ingenAktiveStudier)
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    modifier = Modifier
                                        .align(CenterHorizontally)
                                        .weight(1.2f),
                                    text = str, style = Typography.body1
                                )
                            }
                        } else { // not empty list
                            LazyColumn(contentPadding = PaddingValues(start = 17.dp, end = 17.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(trials1) {
                                    var showDialog by remember{ mutableStateOf(false)}
                                    if(showDialog){
                                       Popup(alignment = Alignment.Center,
                                           onDismissRequest = {showDialog = false},
                                       ) {
                                            InfoDisplay(trialsViewModel.currentNavTrial ,userViewModel = userViewModel){showDialog = false}
                                        }
                                    }
                                    val onClick: () -> Unit =
                                        if(pagerState.currentPage == 0) {
                                            {
                                              trialsViewModel.setCurrentNavTrialID(it)
                                              showDialog = !showDialog
                                            }
                                        }
                                        else
                                            { {trialsViewModel.unsubscribeFromTrial(it) } }
                                    if(pagerState.currentPage == 0) // mytrials
                                        TrialItem(trial = it, iconUsed = TrialPostIcons.Contact, buttonEnabled = false,navHostController = navHostController, iconOnClick = onClick, applyOnClick = {},
                                            readMoreOnClick = {
                                                trialsViewModel.setCurrentNavTrialID(it)
                                                navHostController.navigate("ReadMoreTrialPost") {
                                                    launchSingleTop = true
                                                }
                                            })
                                    else { // subscribedTrials
                                        val canApply = !myTrials.contains(it)
                                        TrialItem(trial = it, iconUsed = TrialPostIcons.NotificationOff, buttonEnabled = canApply,navHostController = navHostController, iconOnClick = onClick,
                                            applyOnClick = {
                                            trialsViewModel.setCurrentNavTrialID(it)
                                            navHostController.navigate("DeltagerInfo") {
                                                launchSingleTop = true
                                            } }
                                        , readMoreOnClick = {
                                                trialsViewModel.setCurrentNavTrialID(it)
                                                navHostController.navigate("ReadMoreTrialPost") {
                                                    launchSingleTop = true
                                                }
                                            })
                                    }
                                    if (trials1.indexOf(element = it) != trials1.lastIndex)
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
@Composable
fun InfoDisplay(trial: Trial?, userViewModel: UserViewModel, onClick: () -> Unit) {

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .border(4.dp, StrokeColor, RoundedCornerShape(10.dp))
    ){ //card content
        Column(
            modifier=Modifier.padding(start = 24.dp, bottom = 24.dp)
        ) {
            Row(horizontalArrangement = Arrangement.End) {
                Text(text = stringResource(R.string.Kontakt), style = Typography.h3,
                    modifier = Modifier.padding(top = 20.dp))
                IconButton(onClick = { onClick() }) {
                    Icon(Icons.Default.Close, contentDescription = stringResource(R.string.Close))
                }
            }
            if (trial != null) {
                userViewModel.getViewModelUserData(trial.researcherID)
                val user = userViewModel.otherUserDataFlow.collectAsState().value

                Text (text = stringResource(R.string.navn) +": "+ user.name +" "+ user.lastName,
                    modifier = Modifier.padding(bottom = 2.dp))
                Text (text=stringResource(R.string.email) +": "+ user.email,
                    modifier = Modifier.padding(bottom = 2.dp))
                //Text(text="tilknyttet hospital: "+user.department)
                Text(text=stringResource(R.string.telefon) +": "+ user.phone)
            }

        }
    }
}

// source: the horizontal pager tabs were inspired by: https://www.youtube.com/watch?v=D6WNjEu6Y9c
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
private fun PostNewTrialButton(modifier: Modifier, newTrialOnClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = newTrialOnClick,
        shape = RoundedCornerShape(6.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 8.dp,
            pressedElevation = 10.dp ),
        colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColorGreen)
    ) {
        Row {
            Icon(
                Icons.Default.Add,
                contentDescription = stringResource(R.string.add),
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
fun ResearcherTrialPost(trial: Trial, numRegisteredParticipants: Int, numEligible: Int, manageTrialOnClick: () -> Unit) {
    val shape = RoundedCornerShape(10.dp)
    Card(
        elevation = 4.dp,
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, shape)
    ) {
        Row (modifier = Modifier.padding(top = 5.dp, end= 5.dp, bottom = 5.dp, start = 10.dp),
            Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically){
            Column(modifier = Modifier
                .weight(3f),
                verticalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = trial.title,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = stringResource(R.string.tilmeldingsfrist) + " "+ trial.registrationDeadline,
                    style = MaterialTheme.typography.body2)
                Text(
                    text = stringResource(R.string.antalTilmeldte) + " "+ numRegisteredParticipants,
                    style = MaterialTheme.typography.body2)
                Text(
                    text = stringResource(R.string.potentielleKandidater) + " "+ numEligible,
                    style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(1.dp))
            }
            Spacer(modifier = Modifier.weight(.1f))
            Column(modifier = Modifier
                .width(130.dp)
                .padding(top = 8.dp, bottom = 8.dp, end = 3.dp)) {
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth()
                        .height(35.dp),
                    onClick = manageTrialOnClick,
                    shape = RoundedCornerShape(6.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 10.dp ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColorGreen)
                ) {
                    Text(stringResource(R.string.haandterStudie), style = Typography.button,
                        fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth()
                        .height(35.dp),
                    onClick = { /*TODO anmod*/  },
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
        //MyTrials(role = Role.RESEARCHER)
    }
}