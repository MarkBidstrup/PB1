package com.example.pb1_probe_application.ui
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pb1_probe_application.dataClasses.Trial
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReadMoreTrialPostScreen(
    trial: Trial,
    onClickNav: () -> Unit,
) {
    val data = trial.deltagerInformation
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onClickNav
                ) {
                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = "Exit"
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(17.dp)
                    .padding(bottom = 20.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Text(text = data, lineHeight = 24.sp)
            }
        }
    )
}
