package composable.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun UiButton(imageVector: ImageVector, modifier: Modifier = Modifier.size(55.dp), onClick: () -> Unit) {
    Button(onClick = onClick, modifier = modifier) {
        Icon(imageVector = imageVector, contentDescription = null, modifier = Modifier.fillMaxSize())
    }
}