package com.ivan.organizer.ui.screens.conversation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateListOf
import com.ivan.organizer.R

class ConversationUiState(
    val channelName: String,
    val channelMembers: Int,
    initialMessages: List<Message>
) {
    private val _messages: MutableList<Message> =
        mutableStateListOf(*initialMessages.toTypedArray())
    val messages: List<Message> = _messages

    fun addMessage(msg: Message) {
        _messages.add(0, msg) // Add to the beginning of the list
    }
}

@Immutable
data class Message(
    val author: String,
    val content: String,
    val timestamp: String,
    val image: Int? = null,
    val authorImage: Int =
        if (author == "me") R.drawable.ic_baseline_add_task_24
        else R.drawable.ic_baseline_calendar_month_24
)