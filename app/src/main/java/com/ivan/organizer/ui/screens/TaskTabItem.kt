package com.ivan.organizer.ui.screens

import androidx.compose.runtime.Composable
import com.ivan.organizer.navigation.conversationTestUiState
import com.ivan.organizer.ui.screens.conversation.ConversationContent
import com.ivan.organizer.ui.screens.tasktabs.TaskReminders

typealias ComposableFun = @Composable () -> Unit

sealed class TaskTabItem(var title: String, var screen: ComposableFun){
    object Chat : TaskTabItem("Чат", {
        ConversationContent(
            uiState = conversationTestUiState
        ) {}
    })
    object Details : TaskTabItem("Детали", { TaskDetails() })
    object Files: TaskTabItem("Файлы", { TaskFiles() })
    object Reminders: TaskTabItem("Напоминания", { TaskReminders() })
}
