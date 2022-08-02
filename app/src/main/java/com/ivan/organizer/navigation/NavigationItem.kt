package com.ivan.organizer.navigation

import com.ivan.organizer.R

sealed class NavigationItem(val route: String, val icon: Int, val title: String) {

    object Focus: NavigationItem(
        "focus_screen", R.drawable.circle, "Фокус")

    object Notes: NavigationItem(
        "notes_screen", R.drawable.ic_baseline_note_alt_24, "Заметки")

    object Tasks: NavigationItem(
        "main_screen", R.drawable.ic_baseline_add_task_24, "Дела")

    object Calendar: NavigationItem(
        "calendar", R.drawable.ic_baseline_calendar_month_24, "Встречи")

    object People: NavigationItem(
        "people", R.drawable.ic_baseline_people_24, "Люди")
}
