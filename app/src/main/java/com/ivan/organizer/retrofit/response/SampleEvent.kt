package com.ivan.organizer.retrofit.response

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

data class SampleEvent(
    val name: String,
    val color: Color,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val description: String? = null
)
