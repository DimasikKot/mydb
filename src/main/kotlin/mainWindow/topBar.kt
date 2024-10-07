package mainWindow

import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sms
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewModels.NavigationViewModel

@Composable
fun TopBar(
    navVM: NavigationViewModel,
    unit1: () -> Unit = { navVM.currentPage = 1 },
    unit2: () -> Unit = { navVM.currentPage = 2 },
    unit3: () -> Unit = { navVM.currentPage = 3 },
    modifier: Modifier = Modifier.height(60.dp)
) {
    BottomAppBar(modifier = modifier) {
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Face, toString(), modifier = Modifier.weight(1f)) },
            label = { Text("Face") },
            selected = navVM.currentPage == 1,
            onClick = unit1
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Sms, toString(), modifier = Modifier.weight(1f)) },
            label = { Text("Sms") },
            selected = navVM.currentPage == 2,
            onClick = unit2
        )
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Settings, toString(), modifier = Modifier.weight(1f)) },
            label = { Text("Settings") },
            selected = navVM.currentPage == 3,
            onClick = unit3
        )
    }
}