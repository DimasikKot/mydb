package composable.db

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import composable.ui.UiTab
import data.viewModels.NavigationViewModel

@Composable
fun TablesBar(navVM: NavigationViewModel, modifier: Modifier = Modifier.width(200.dp)) {
    Card(elevation = 5.dp, modifier = modifier.padding(10.dp)) {
        Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
            NavigationRailItem(
                icon = { UiTab(Icons.Default.Style, "Типы устройств", navVM.currentPage == 1) },
                selected = navVM.currentPage == 1,
                onClick = { navVM.currentPage = 1 },
                modifier = Modifier.weight(1f).fillMaxSize()
            )
            NavigationRailItem(
                icon = { UiTab(Icons.Default.Devices, "Устройства", navVM.currentPage == 2) },
                selected = navVM.currentPage == 2,
                onClick = { navVM.currentPage = 2 },
                modifier = Modifier.weight(1f).fillMaxSize()
            )
            NavigationRailItem(
                icon = { UiTab(Icons.Default.People, "Сотрудники", navVM.currentPage == 3) },
                selected = navVM.currentPage == 3,
                onClick = { navVM.currentPage = 3 },
                modifier = Modifier.weight(1f).fillMaxSize()
            )
            NavigationRailItem(
                icon = { UiTab(Icons.Default.Groups, "Отделы", navVM.currentPage == 4) },
                selected = navVM.currentPage == 4,
                onClick = { navVM.currentPage = 4 },
                modifier = Modifier.weight(1f).fillMaxSize()
            )
            NavigationRailItem(
                icon = { UiTab(Icons.Default.Newspaper, "Учёт", navVM.currentPage == 5) },
                selected = navVM.currentPage == 5,
                onClick = { navVM.currentPage = 5 },
                modifier = Modifier.weight(1f).fillMaxSize()
            )
        }
    }
}