package composable.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UiText(
    modifier: Modifier = Modifier,
    text: String = "null",
    style: TextStyle = MaterialTheme.typography.h5,
) {
    var active by remember { mutableStateOf(false) }
    Text(text = text,
        style = style,
        maxLines = if (!active) 1 else Int.MAX_VALUE,
        minLines = if (!active) 1 else 2,
        modifier = modifier.animateContentSize().onPointerEvent(PointerEventType.Enter) { active = true }
            .onPointerEvent(PointerEventType.Exit) { active = false })
}