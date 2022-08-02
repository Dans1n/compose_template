package com.ivan.organizer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivan.organizer.R
import kotlin.text.Typography.nbsp

@Composable
@Preview
fun ReceivedMessage(){

    val messageText = remember{mutableStateOf(String())}

    messageText.value = "akkkkkkkkkkkkkkkkkkkkkkkk"

    Surface(shape = RoundedCornerShape(10.dp)) {
        Box(
            modifier = Modifier
                .background(Color.LightGray)
                .widthIn(0.dp, 273.dp),
            contentAlignment = Alignment.BottomEnd) {
            Text(
                modifier = Modifier
                    .padding(10.dp),
                text = "${messageText.value} $nbsp$nbsp$nbsp$nbsp$nbsp$nbsp$nbsp$nbsp$nbsp$nbsp$nbsp",
                color = Color.Black, textAlign = TextAlign.Left)
            Text(
                modifier = Modifier
                    .widthIn(0.dp, 273.dp)
                    .padding(bottom = 5.dp, end = 20.dp),
                text = "15:00",
                textAlign = TextAlign.Right,
                fontSize = 10.sp)

            Image(
                painter = painterResource(id = R.drawable.ic_baseline_check_24),
                contentDescription = "",
                modifier = Modifier
                    .scale(0.5f),
                alignment = Alignment.Center)
        }
    }
}