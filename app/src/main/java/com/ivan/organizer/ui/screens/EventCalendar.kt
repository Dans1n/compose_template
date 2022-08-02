package com.ivan.organizer.ui.screens

import android.widget.CalendarView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun EventCalendar() {

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            CalendarView(context).apply {
            }
        })
}