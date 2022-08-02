package com.ivan.organizer.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ivan.organizer.data.exampleUiState
import com.ivan.organizer.ui.screens.*
import com.ivan.organizer.ui.screens.conversation.ConversationContent
import com.ivan.organizer.ui.screens.conversation.ConversationUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi

val conversationTestUiState = ConversationUiState(
    initialMessages = (exampleUiState.messages.plus(exampleUiState.messages)),
    channelName = "#composers",
    channelMembers = 42
)

val springSpec = spring<IntOffset>(dampingRatio = Spring.DampingRatioMediumBouncy)
val tweenSpec = tween<IntOffset>(durationMillis = 2000, easing = CubicBezierEasing(0.08f,0.93f,0.68f,1.27f))

@OptIn(ExperimentalAnimationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun Navigation(){
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(
            route = Screen.MainScreen.route,
        ) {
            CardsScreen(navController)
            //TaskList(navController = navController)
        }
        composable(
            route = Screen.ChatScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),
        ) { entry ->
            ConversationContent(
                uiState = conversationTestUiState
            ) {}
        }
        composable(
            route = Screen.TaskScreen.route + "/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { entry ->
            TaskScreen(name = entry.arguments?.getString("name")!!, navController)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavigation(navController: NavHostController){

    AnimatedNavHost(navController, startDestination = NavigationItem.Tasks.route) {

        composable(NavigationItem.Focus.route) {
            FocusScreen()
        }

        composable(NavigationItem.Notes.route){
            NotesScreen()
        }

        composable(NavigationItem.Tasks.route) {
            Navigation()
        }

        composable(NavigationItem.Calendar.route) {
            SchedulePreview()
        }

        composable(NavigationItem.People.route) {
            People()
        }
    }
}