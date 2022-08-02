package com.ivan.organizer.ui.customview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable() () -> Unit)? = null,
    trailingIcon: (@Composable() () -> Unit)? = null
) {
    var text by rememberSaveable { mutableStateOf("") }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        Box(
            modifier = Modifier.weight(1f)
                .padding(start = paddingLeadingIconEnd, end = paddingTrailingIconStart)
        ) {
            TextField(
                value = text,
                onValueChange = { text = it }
            )
            if (text.isEmpty()) {
                Text(
                    text = "Placeholder"
                )
            }
        }
        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}