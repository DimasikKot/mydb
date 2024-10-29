package composable.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun UiTab(
    imageVector: ImageVector,
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Card(elevation = if (selected) 25.dp else 3.dp, modifier = modifier.padding(if (selected) 6.dp else 8.dp)) {
        Row(modifier = Modifier.background(if (selected) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.background).fillMaxSize().padding(start = 5.dp).padding(if (selected) 4.dp else 2.dp)) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(40.dp).fillMaxSize().align(Alignment.CenterVertically)
            )
            Text(text, style = MaterialTheme.typography.button, modifier = Modifier.fillMaxWidth().align(Alignment.CenterVertically).padding(start = 10.dp))
        }
    }
}