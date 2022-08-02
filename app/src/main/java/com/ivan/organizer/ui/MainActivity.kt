package com.ivan.organizer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ivan.organizer.navigation.NavigationItem
import com.ivan.organizer.ui.screens.conversation.LocalBackPressedDispatcher
import com.ivan.organizer.ui.theme.blue
import com.ivan.organizer.ui.theme.iconGrey
import com.ivan.organizer.viewmodel.CardsScreenViewModel

class MainActivity : ComponentActivity() {

    val cardModel: CardsScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides onBackPressedDispatcher
            ) {
                ScreenMain()
            }
        }
        /*lifecycleScope.launch(Dispatchers.IO) {
            delay(10000)
            deleteDatabase("tasks")
        }*/
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScreenMain() {
    val navController = rememberAnimatedNavController()

    Scaffold(bottomBar = { BottomNavigationBar(navController) }) {
        Box(modifier = Modifier.padding(it)){
            com.ivan.organizer.navigation.BottomNavigation(navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    
    val items = listOf(
        NavigationItem.Focus,
        NavigationItem.Notes,
        NavigationItem.Tasks,
        NavigationItem.Calendar,
        NavigationItem.People,
    )
    
    BottomNavigation(
        backgroundColor = Color.White,
        modifier = Modifier.height(48.dp)) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = blue,
                unselectedContentColor = iconGrey,
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}