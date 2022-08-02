package com.ivan.organizer.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ivan.organizer.R
import com.ivan.organizer.navigation.Screen
import com.ivan.organizer.ui.theme.cardCollapsedBackgroundColor
import com.ivan.organizer.ui.theme.cardExpandedBackgroundColor
import com.ivan.organizer.viewmodel.CardModel
import com.ivan.organizer.viewmodel.CardsScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.getViewModel
import kotlin.math.roundToInt

const val ACTION_ITEM_SIZE = 56
const val CARD_HEIGHT = 56
const val CARD_OFFSET = 168f
const val ANIMATION_DURATION = 500
const val MIN_DRAG_AMOUNT = 6


@Composable
fun CardTitle(cardTitle: String) {
    Text(
        text = cardTitle,
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center,
    )
}

@Composable
fun ActionsRow(
    actionIconSize: Dp,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onFavorite: () -> Unit,
) {
    Row(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        IconButton(
            modifier = Modifier.size(actionIconSize),
            onClick = {
                onDelete()
            },
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_task_24),
                    tint = Color.Gray,
                    contentDescription = "delete action",
                )
            }
        )
    }
}

@ExperimentalCoroutinesApi
@Composable
fun CardsScreen(navController: NavController) {

    val viewModel = getViewModel<CardsScreenViewModel>()
    val cards = viewModel.cards.collectAsState()
    val revealedCardIds = viewModel.revealedCardIdsList.collectAsState()

    Scaffold(backgroundColor = Color.White) {
        LazyColumn {
            itemsIndexed(cards.value, key = { _, item -> item.id }) { _, card ->
                Box(Modifier.fillMaxWidth()) {
                    ActionsRow(
                        actionIconSize = ACTION_ITEM_SIZE.dp,
                        onDelete = {},
                        onEdit = {},
                        onFavorite = {}
                    )
                    DraggableCardSimple(
                        card = card,
                        isRevealed = revealedCardIds.value.contains(card.id),
                        cardHeight = CARD_HEIGHT.dp,
                        cardOffset = CARD_OFFSET,
                        onExpand = { viewModel.onItemExpanded(card.id) },
                        onCollapse = { viewModel.onItemCollapsed(card.id) },
                        navController = navController
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCard(
    card: CardModel,
    isRevealed: Boolean,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
) {
    val offsetX = remember { mutableStateOf(0f) }
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, label = "")
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) cardOffset - offsetX.value else -offsetX.value },
    )

    Card(
        modifier = Modifier
            .offset { IntOffset((offsetX.value + offsetTransition).roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    val original = Offset(offsetX.value, 0f)
                    val summed = original + Offset(x = dragAmount, y = 0f)
                    val newValue = Offset(x = summed.x.coerceIn(0f, cardOffset), y = 0f)
                    if (newValue.x >= 10) {
                        onExpand()
                        return@detectHorizontalDragGestures
                    } else if (newValue.x <= 0) {
                        onCollapse()
                        return@detectHorizontalDragGestures
                    }
                    change.consumePositionChange()
                    offsetX.value = newValue.x
                }
            },
        content = { CardTitle(cardTitle = card.title) }
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCardSimple(
    card: CardModel,
    cardHeight: Dp,
    isRevealed: Boolean,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    navController: NavController
) {
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, "cardTransition")

    /*val cardBgColor by transition.animateColor(
        label = "cardBgColorTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = {
            if (isRevealed) cardExpandedBackgroundColor else cardCollapsedBackgroundColor
        }
    )*/
    val offsetTransition by transition.animateDp(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) 72.dp else 0.dp },

        )
    /*val cardElevation by transition.animateDp(
        label = "cardElevation",
        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        targetValueByState = { if (isRevealed) 2.dp else 2.dp }
    )*/

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp)
            .height(61.dp)
            .offset(offsetTransition, 0.dp)
            .clickable {
                navController.navigate(Screen.TaskScreen.withArgs(card.title))
            }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount >= MIN_DRAG_AMOUNT -> onExpand()
                        dragAmount < -MIN_DRAG_AMOUNT -> onCollapse()
                    }
                }
            },
        backgroundColor = Color.LightGray,
        shape = RoundedCornerShape(0.dp),
        content = { CardTitle(cardTitle = card.title) }
    )
}