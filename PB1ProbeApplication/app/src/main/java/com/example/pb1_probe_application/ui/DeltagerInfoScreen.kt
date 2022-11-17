package com.example.pb1_probe_application.ui

import android.annotation.SuppressLint
import android.graphics.Paint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pb1_probe_application.R
import com.example.pb1_probe_application.data.Datasource
import com.example.pb1_probe_application.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeltagerInfo(trialID: String, navHostController: NavHostController = rememberNavController()) {
    // TODO - get data from database based on the trialID
    val data = Datasource().loadDeltagerInfo()

    var consentBoxChecked by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState() // alternatively used to enable "apply" button

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(stringResource(R.string.deltager_info), style = Typography.h1) },
                backgroundColor = MaterialTheme.colors.onPrimary)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {
                //TODO - go back
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "back arrow"
                    )
                }
            }
        },
        content = {
            Column(modifier = Modifier
                .padding(17.dp)
                .padding(bottom = 20.dp)
                .fillMaxSize()
                .verticalScroll(scrollState) ){
                Text(text = data, lineHeight = 24.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Row (modifier = Modifier.padding(end = 20.dp)){
                    Checkbox(
                        checked = consentBoxChecked,
                        onCheckedChange = { consentBoxChecked = it }
                    )
                    TextWithHyperlink()
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        },
        bottomBar = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                TrialApplyButton(
                    consentBoxChecked, // scrollState.value == scrollState.maxValue
                    onClick = {
                        // TODO - update state
                        navHostController.navigate("Applied")
                })
            }
        }
        )
}


// code below is from https://stackoverflow.com/questions/65567412/jetpack-compose-text-hyperlink-some-section-of-the-text
// Thracian's reply on Jan 10, 2021
@Composable
fun TextWithHyperlink() {
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {

        val str = stringResource(R.string.samtykkeerklaering)
        val startIndex = str.indexOf("http")
        val endIndex = str.length
        append(str)
        addStyle(
            style = SpanStyle(
                color = Color(0xff64B5F6),
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline
            ), start = startIndex, end = endIndex
        )

        // attach a string annotation that stores a URL
        addStringAnnotation(
            tag = "URL",
            annotation = "https://probe.dk/privatlivspolitik/",
            start = startIndex,
            end = endIndex
        )

    }

// UriHandler parse and opens URI inside AnnotatedString Item in Browse
    val uriHandler = LocalUriHandler.current

// Clickable text returns position of text that is clicked in onClick callback
    ClickableText(
        modifier = Modifier.fillMaxWidth(),
        text = annotatedLinkString,
        style = TextStyle(color = MaterialTheme.colors.onBackground, fontSize = 15.sp,
            lineHeight = 20.sp),
        onClick = {
            annotatedLinkString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}