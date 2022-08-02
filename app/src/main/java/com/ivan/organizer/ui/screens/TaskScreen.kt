package com.ivan.organizer.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import com.ivan.organizer.R
import kotlinx.coroutines.launch


@Composable
fun TaskTopBar(name: String, navController: NavController) {
    TopAppBar(
        title = { Text(text = name, fontSize = 18.sp) },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_arrow_back_24),
                    contentDescription = "")
            }
        }
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TaskScreen(name: String, navController: NavController) {

    val tabs = listOf(
        TaskTabItem.Chat,
        TaskTabItem.Details,
        TaskTabItem.Files,
        TaskTabItem.Reminders
    )

    val pagerState = rememberPagerState()
    Scaffold(
        topBar = { TaskTopBar(name, navController) },
    ) {
        Column {
            Tabs(tabs = tabs, pagerState = pagerState)
            TaskTabsContent(tabs = tabs, pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TaskTabItem>, pagerState: PagerState){

    val scope = rememberCoroutineScope()

    Surface(elevation = 10.dp) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Color.White,
            contentColor = Color.White,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            tabs.forEachIndexed{ index, tab ->
                Tab(
                    text = {
                        Text(
                            text = tab.title,
                            fontSize = 14.sp,
                            color = if (pagerState.currentPage == index) Color.Black else Color.Gray
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch {
                        pagerState.animateScrollToPage(index)
                    } })
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TaskTabsContent(tabs: List<TaskTabItem>, pagerState: PagerState){
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}