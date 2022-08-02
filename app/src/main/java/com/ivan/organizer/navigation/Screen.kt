package com.ivan.organizer.navigation

sealed class Screen(val route: String){
    object MainScreen: Screen("main_screen")
    object ChatScreen: Screen("chat_screen")
    object TaskScreen: Screen("task")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
