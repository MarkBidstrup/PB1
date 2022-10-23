package com.example.pb1_probe_application.ui

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.model.TrialState
import com.example.pb1_probe_application.model.TrialsViewModel
import com.example.pb1_probe_application.ui.theme.ButtonColorGreen
import com.example.pb1_probe_application.ui.theme.NavBarColorGreen
import com.example.pb1_probe_application.ui.theme.Typography

@Composable
fun MyTrialsResearcher( trialsViewModel: TrialsViewModel = viewModel()) {
    val trials by trialsViewModel.uiState.collectAsState()
// TODO - check when/ how often things recompose when using kotlin flows
    TrialsList(trials = trials)
}

@Composable
fun TrialsList(trials: List<TrialState>, modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.mineStudier), style = Typography.h1)
                },
                backgroundColor = androidx.compose.ui.graphics.Color.White)

        },
        content = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp)) {
                Text(
                    modifier = Modifier.padding(start=16.dp),
                    text = stringResource(R.string.aktiveStudier), style = Typography.h2
                )
                if(trials.isEmpty()) {
                    Spacer(modifier = Modifier.height(250.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = stringResource(R.string.ingenStudier), style = Typography.body1
                    )
                } else {
                    LazyColumn(
                        modifier = modifier
                            .weight(5f),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(trials) { trialsPosts ->
                            ResearcherTrialPost(trialsPosts)
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(.2f))
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(40.dp),
                    onClick = { /*TODO*/  },
                    shape = RoundedCornerShape(6.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 8.dp ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColorGreen)
                ) {
                    Row( ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "add"
                        )
                        Spacer(modifier = Modifier.padding(1.dp))
                        Text(stringResource(R.string.opretStudie), style = Typography.body1, fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = NavBarColorGreen
            ) {
                Text(stringResource(R.string.placeholder))
            }
        }
    )
}

@Composable
fun ResearcherTrialPost(trialInfo: TrialState, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(10.dp)
    Card(elevation = 4.dp,
        modifier = Modifier
        .fillMaxWidth()
        .clip(shape)
        .border(1.dp, Color.LightGray, shape))
         {
        Row (modifier = Modifier.padding(top = 5.dp, end= 5.dp, bottom = 5.dp, start = 10.dp),
            Arrangement.SpaceEvenly){
            Column(modifier = Modifier.height(110.dp)
                                        .weight(3f),
                verticalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = trialInfo.trialName,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp
                )
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
            Column(modifier = modifier
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
                    Text(stringResource(R.string.haandterStudie), style = Typography.button, fontWeight = FontWeight.Bold)
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
                    Text(stringResource(R.string.anmod), style = Typography.button, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ResearcherTrialsScreenPreview() {
    MyTrialsResearcher()
}