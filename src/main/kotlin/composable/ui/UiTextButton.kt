package composable.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun UiTextButton(
    textOnText: String,
    onClick: () -> Unit,
    textOnButton: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(border = BorderStroke(0.dp, MaterialTheme.colors.primary), shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Text(
            textOnText,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.weight(1f).align(alignment = Alignment.CenterVertically)
        )
        Button(
            onClick = onClick,
            modifier = Modifier.align(alignment = Alignment.CenterVertically).padding(start = 10.dp)
        ) {
            Text(
                textOnButton,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
        }
    }
}