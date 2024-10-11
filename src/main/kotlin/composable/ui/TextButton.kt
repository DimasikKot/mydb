package composable.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun UiTextButton(
    textText: String,
    onClick: () -> Unit,
    onButton: @Composable (RowScope.() -> Unit) = { Text(textText) },
    modifier: Modifier = Modifier
        .padding(10.dp)
        .border(border = BorderStroke(1.dp, MaterialTheme.colors.primary), shape = RoundedCornerShape(10.dp))
        .padding(20.dp)
) {
    Row(modifier = modifier) {
        Text(textText, modifier = Modifier.align(alignment = Alignment.CenterVertically))
        Button(onClick = onClick, content = onButton, modifier = Modifier.padding(start = 20.dp))
    }
}