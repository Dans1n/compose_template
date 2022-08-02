package com.ivan.organizer.ui.screens.conversation

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivan.organizer.R

enum class InputSelector {
    NONE,
    EMOJI,
    PICTURE
}

enum class EmojiStickerSelector {
    EMOJI,
    STICKER
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserInput(
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
    resetScroll: () -> Unit = {},
) {
    var selectorVisibility by remember { mutableStateOf(false) }
    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }

    val dismissKeyboard = {
        currentInputSelector = InputSelector.NONE
        selectorVisibility = false
    }

    // Intercept back navigation if there's a InputSelector visible
    if (currentInputSelector != InputSelector.NONE) {
        BackPressHandler(onBackPressed = dismissKeyboard)
        selectorVisibility = true
    }

    var textState by remember { mutableStateOf(TextFieldValue()) }

    // Used to decide if the keyboard should be shown
    var textFieldFocusState by remember { mutableStateOf(false) }

    val disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)

    val buttonColors = ButtonDefaults.buttonColors(
        disabledContainerColor = Color.Transparent,
        disabledContentColor = disabledContentColor
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

            UserInputSelector(
                onSelectorChange = { currentInputSelector = it },
                currentInputSelector = currentInputSelector
            )

            UserInputText(
                textFieldValue = textState,
                onTextChanged = { textState = it },
                // Only show the keyboard if there's no input selector and text field has focus
                keyboardShown = currentInputSelector == InputSelector.NONE && textFieldFocusState,
                // Close extended selector if text field receives focus
                onTextFieldFocused = { focused ->
                    if (focused) {
                        currentInputSelector = InputSelector.NONE
                        resetScroll()
                    }
                    textFieldFocusState = focused
                },
                focusState = textFieldFocusState
            )

            Button(
                modifier = Modifier.height(40.dp)
                    .align(Alignment.CenterVertically),
                enabled = textState.text.isNotBlank(),
                onClick = {
                    onMessageSent(textState.text)
                    textState = TextFieldValue()
                    resetScroll()
                    dismissKeyboard()
                },
                colors = buttonColors,
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    stringResource(id = R.string.send),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        AnimatedVisibility(visible = selectorVisibility) {
            SelectorExpanded(
                onCloseRequested = dismissKeyboard,
                onTextAdded = { textState = textState.addText(it) },
                currentSelector = currentInputSelector
            )
        }
    }
}

private fun TextFieldValue.addText(newString: String): TextFieldValue {
    val newText = this.text.replaceRange(
        this.selection.start,
        this.selection.end,
        newString
    )
    val newSelection = TextRange(
        start = newText.length,
        end = newText.length
    )

    return this.copy(text = newText, selection = newSelection)
}

@Composable
private fun SelectorExpanded(
    currentSelector: InputSelector,
    onCloseRequested: () -> Unit,
    onTextAdded: (String) -> Unit
) {
    if (currentSelector == InputSelector.NONE) return

    val focusRequester = FocusRequester()

    SideEffect {
        if (currentSelector == InputSelector.EMOJI) {
            focusRequester.requestFocus()
        }
    }

    Surface(tonalElevation = 8.dp) {
        when (currentSelector) {
            InputSelector.EMOJI -> EmojiSelector(onTextAdded, focusRequester)
            InputSelector.PICTURE -> FunctionalityNotAvailablePanel()
            else -> { throw NotImplementedError() }
        }
    }
}

@Composable
fun FunctionalityNotAvailablePanel() {
    AnimatedVisibility(
        visibleState = remember { MutableTransitionState(false).apply { targetState = true } },
        enter = expandHorizontally() + fadeIn(),
        exit = shrinkHorizontally() + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .height(320.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.not_available),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(id = R.string.not_available_subtitle),
                modifier = Modifier.paddingFrom(FirstBaseline, before = 32.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun UserInputSelector(
    onSelectorChange: (InputSelector) -> Unit,
    currentInputSelector: InputSelector,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        InputSelectorButton(
            onClick = { onSelectorChange(InputSelector.EMOJI) },
            painterResource(id = R.drawable.ic_baseline_mood_24),
            selected = currentInputSelector == InputSelector.EMOJI,
            description = ""
        )
        InputSelectorButton(
            onClick = { onSelectorChange(InputSelector.PICTURE) },
            painterResource(id = R.drawable.ic_baseline_add_photo),
            selected = currentInputSelector == InputSelector.PICTURE,
            description = ""
        )
    }
}

@Composable
private fun InputSelectorButton(
    onClick: () -> Unit,
    icon: Painter,
    description: String,
    selected: Boolean
) {
    val backgroundModifier = if (selected) {
        Modifier.background(
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(14.dp)
        )
    } else {
        Modifier
    }
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(56.dp)
            .then(backgroundModifier)
    ) {
        val tint = if (selected) {
            MaterialTheme.colorScheme.onSecondary
        } else {
            MaterialTheme.colorScheme.secondary
        }
        Icon(
            icon,
            tint = tint,
            modifier = Modifier.padding(16.dp),
            contentDescription = description
        )
    }
}

@Composable
private fun NotAvailablePopup(onDismissed: () -> Unit) {
    FunctionalityNotAvailablePopup(onDismissed)
}

val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey

@ExperimentalFoundationApi
@Composable
private fun UserInputText(
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
    keyboardShown: Boolean,
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean
) {
    val a11ylabel = stringResource(id = R.string.textfield_desc)
    Row(
        modifier = Modifier
            .height(64.dp)
            .semantics {
                contentDescription = a11ylabel
                keyboardShownProperty = keyboardShown
            },
        horizontalArrangement = Arrangement.End
    ) {
        Surface {

            Box(
                modifier = Modifier
                    .height(64.dp)
                    .weight(1f)
                    .padding(top = 5.dp, bottom = 5.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(15.dp))
            ) {

                var lastFocusState by remember { mutableStateOf(false) }

                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { onTextChanged(it) },
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.CenterStart)
                        .padding(5.dp)
                        .onFocusChanged { state ->
                            if (lastFocusState != state.isFocused) {
                                onTextFieldFocused(state.isFocused)
                            }
                            lastFocusState = state.isFocused
                        },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = ImeAction.Send
                    ),
                    maxLines = 1,
                    cursorBrush = SolidColor(LocalContentColor.current),
                    textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current)
                )

                val disableContentColor =
                    MaterialTheme.colorScheme.onSurfaceVariant
                if (textFieldValue.text.isEmpty() && !focusState) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(5.dp),
                        text = stringResource(id = R.string.textfield_hint),
                        style = MaterialTheme.typography.bodyLarge.copy(color = disableContentColor)
                    )
                }
            }
        }
    }
}

@Composable
fun EmojiSelector(
    onTextAdded: (String) -> Unit,
    focusRequester: FocusRequester
) {
    var selected by remember { mutableStateOf(EmojiStickerSelector.EMOJI) }

    val a11yLabel = stringResource(id = R.string.emoji_selector_desc)
    Column(
        modifier = Modifier
            .focusRequester(focusRequester)
            .focusTarget()
            .semantics { contentDescription = a11yLabel }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            ExtendedSelectorInnerButton(
                text = stringResource(id = R.string.emojis_label),
                onClick = { selected = EmojiStickerSelector.EMOJI },
                selected = true,
                modifier = Modifier.weight(1f)
            )
            ExtendedSelectorInnerButton(
                text = stringResource(id = R.string.stickers_label),
                onClick = { selected = EmojiStickerSelector.STICKER },
                selected = false,
                modifier = Modifier.weight(1f)
            )
        }
        Row(modifier = Modifier.verticalScroll(rememberScrollState())) {
            EmojiTable(onTextAdded, modifier = Modifier.padding(8.dp))
        }
    }
    if (selected == EmojiStickerSelector.STICKER) {
        NotAvailablePopup(onDismissed = { selected = EmojiStickerSelector.EMOJI })
    }
}

@Composable
fun ExtendedSelectorInnerButton(
    text: String,
    onClick: () -> Unit,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
        disabledContainerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface,
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.74f)
    )
    TextButton(
        onClick = onClick,
        modifier = modifier
            .padding(8.dp)
            .height(36.dp),
        enabled = selected,
        colors = colors,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun EmojiTable(
    onTextAdded: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        repeat(4) { x ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(EMOJI_COLUMNS) { y ->
                    val emoji = emojis[x * EMOJI_COLUMNS + y]
                    Text(
                        modifier = Modifier
                            .clickable(onClick = { onTextAdded(emoji) })
                            .sizeIn(minWidth = 42.dp, minHeight = 42.dp)
                            .padding(8.dp),
                        text = emoji,
                        style = LocalTextStyle.current.copy(
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}

private const val EMOJI_COLUMNS = 10
