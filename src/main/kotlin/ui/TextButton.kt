package ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UiTextButton(
    textText: String,
    onClick: () -> Unit,
    onButton: @Composable (RowScope.() -> Unit) = { Text(textText) },
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(textText)
        Button(onClick = onClick, content = onButton)
    }
}