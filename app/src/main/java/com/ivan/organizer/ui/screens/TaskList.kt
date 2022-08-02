package com.ivan.organizer.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ivan.organizer.data.SampleDataList
import com.ivan.organizer.navigation.Screen
import com.ivan.organizer.retrofit.response.SampleData
import com.ivan.organizer.ui.theme.*
import com.ivan.organizer.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import java.util.*

@Preview
@Composable
fun ListPreview(){
    //MessageCard(data = SampleDataList.list[0], null)
}


@Composable
fun MessageCard(
    data: SampleData,
    navController: NavController?,
    color: Color
){

    Column(Modifier
        .clickable {
            //navController?.navigate(Screen.ChatScreen.withArgs(data.name))
            navController?.navigate(Screen.TaskScreen.withArgs(data.name))
        }
        .padding(top = 13.dp))
    {

        Row() {

            AsyncImage(
                model = "https://cdn.openai.com/research-covers/glow/demo/media/leo.png",
                contentDescription = "Profile Pic",
                modifier = Modifier
                    .padding(start = 11.dp, top = 4.dp)
                    .size(27.dp)
                    .clip(CircleShape))

            Spacer(modifier = Modifier.width(7.dp))

            Column(modifier = Modifier) {
                Text(
                    data.name,
                    color = Color.Black,
                    fontSize = 15.sp)

                Spacer(modifier = Modifier.height(4.dp))

                Row() {

                    Text(
                        data.project, fontSize = 12.sp,
                        color = color)

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        if (data.place.isEmpty()) data.time else "${data.time},",
                        fontSize = 12.sp,
                        color = Color.Black.copy(alpha = 0.4F))

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        data.place,
                        fontSize = 12.sp,
                        color = Color.Black.copy(alpha = 0.4F))
                }
            }
        }
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(top = 9.dp))
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TaskList(navController: NavController){

    val coroutineScope = rememberCoroutineScope()

    val model = getViewModel<MainViewModel>()

    val response by model.response.collectAsState()

    var switch by rememberSaveable { mutableStateOf(false) }
    var visibility by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val collect = model.dataList.collectAsState()

    val focus = SampleDataList.focus

    var openDialog by remember { mutableStateOf(false) }
    var dialogVisibility by remember { mutableStateOf(false) }

    if (openDialog){
        Dialog(
            onDismissRequest = {
                dialogVisibility = false
                openDialog = false
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            LaunchedEffect(true){
                dialogVisibility = true
            }
            AnimatedVisibility(
                visible = dialogVisibility,
                enter = slideInHorizontally(initialOffsetX = {6000}),
                exit = slideOutVertically(targetOffsetY = {1000})
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(color = Color.White)
                ){
                    Text(text = "dadadsadasdada", Modifier.padding(20.dp))
                }
            }
        }
    }

    Column(Modifier
        .scrollable(
            enabled = true,
            orientation = Orientation.Vertical,
            state = scrollState)) {

        LazyColumn(
            Modifier
                .background(grey)
                .fillMaxHeight(.89f)) {
            items(
                if (!switch) collect.value.sortedBy { it.time }
                else collect.value.sortedBy { it.project },
                key = {it.id}) { message ->

                val projectColor = when (message.project){
                    "Личные" -> blue
                    "Термы Мира" -> green
                    else -> orange
                }

                Row(
                    Modifier
                        .animateItemPlacement(animationSpec = tween(300))
                        .height(IntrinsicSize.Min)) {
                    AnimatedVisibility(visible = visibility) {
                        Box(modifier = Modifier
                            .background(projectColor)
                            .width(5.dp)
                            .fillMaxHeight())
                    }

                    MessageCard(message, navController, projectColor)
                }
            }
        }

        Row(
            modifier = Modifier
                .background(lightGrey)
                .fillMaxWidth()) {

            var text by rememberSaveable { mutableStateOf("") }
            var textState by remember { mutableStateOf(TextFieldValue()) }

            Box(modifier = Modifier.height(47.dp)){
                BasicTextField(
                    value = text,
                    onValueChange = {  })
            }

            TextField(
                value = text,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(.89f)
                    .height(32.dp),
                onValueChange = {
                    text = it
                },
                placeholder = {
                    Text(
                        text = "Новая задача...",
                        color = Color.Black.copy(alpha = 0.2f)
                    )
                },
                shape = CircleShape,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    backgroundColor = Color.White
                )
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        model.deleteAll()
                    }

                    openDialog = true
                    model.updateList(SampleData(Random().nextInt(), "ssssss", "aaaa", "20", "sssssssss"))
                },

                shape = CircleShape,
                modifier = Modifier
                    .padding(top = 6.dp)
                    .width(35.dp)
                    .height(35.dp),
                content = { Text(text = "+", textAlign = TextAlign.Center) })
        }
    }
}
