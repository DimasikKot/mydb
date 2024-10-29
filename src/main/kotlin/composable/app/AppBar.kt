package composable.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.ui.UiTab
import data.viewModels.NavigationViewModel

@Composable
fun AppBar(
    navVM: NavigationViewModel,
    unit1: () -> Unit = { navVM.currentPage = 1 },
    unit2: () -> Unit = { navVM.currentPage = 2 },
    modifier: Modifier = Modifier.height(60.dp)
) {
    BottomAppBar(modifier = modifier.fillMaxSize()) {
        BottomNavigationItem(
            icon = { UiTab(Icons.Default.TableRows, "Таблицы", navVM.currentPage == 1) },
            selected = navVM.currentPage == 1,
            onClick = unit1
        )
        BottomNavigationItem(
            icon = { UiTab(Icons.Default.Settings, "Настройки", navVM.currentPage == 2) },
            //label = { Text("Натсройки") },
            selected = navVM.currentPage == 2,
            onClick = unit2
        )
    }
}